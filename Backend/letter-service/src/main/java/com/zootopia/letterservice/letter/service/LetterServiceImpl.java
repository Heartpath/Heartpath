package com.zootopia.letterservice.letter.service;

import com.zootopia.letterservice.common.FCM.FCMService;
import com.zootopia.letterservice.common.FCM.FirebaseCloudMessageService;
import com.zootopia.letterservice.common.error.code.ErrorCode;
import com.zootopia.letterservice.common.error.exception.BadRequestException;
import com.zootopia.letterservice.common.global.BannedWords;
import com.zootopia.letterservice.common.s3.S3Uploader;
import com.zootopia.letterservice.letter.dto.request.FriendReqDto;
import com.zootopia.letterservice.letter.dto.request.LetterHandReqDto;
import com.zootopia.letterservice.letter.dto.request.LetterPlaceReqDto;
import com.zootopia.letterservice.letter.dto.request.LetterTextReqDto;
import com.zootopia.letterservice.letter.dto.response.*;
import com.zootopia.letterservice.letter.entity.LetterImage;
import com.zootopia.letterservice.letter.entity.LetterMongo;
import com.zootopia.letterservice.letter.entity.LetterMySQL;
import com.zootopia.letterservice.letter.entity.PlaceImage;
import com.zootopia.letterservice.letter.repository.LetterImageRepository;
import com.zootopia.letterservice.letter.repository.LetterJpaRepository;
import com.zootopia.letterservice.letter.repository.LetterMongoRepository;
import com.zootopia.letterservice.letter.repository.PlaceImageRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class LetterServiceImpl implements LetterService {
    private final LetterJpaRepository letterJpaRepository;
    private final LetterMongoRepository letterMongoRepository;
    private final LetterImageRepository letterImageRepository;
    private final PlaceImageRepository placeImageRepository;

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final FCMService fcmService;

    private final BannedWords bannedWords;
    private final S3Uploader s3Uploader;

    @Override
    @Transactional
    public void createHandLetter(String accessToken, LetterHandReqDto letterHandReqDto, MultipartFile content, List<MultipartFile> files) {
        UserDetailResDto user = accessTokenToMember(accessToken).getData();
        System.out.println(user.getMemberID());
        if (letterHandReqDto.getReceiverId().isEmpty()) {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_RECEIVER_ID);
        }
        // content 파일
        if (content.isEmpty()) {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_CONTENT);
        }

        String contentUrl = uploadFileToS3(content, "letter-hand-content");

        // letter file
        List<String> fileUrls = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                String fileUrl = uploadFileToS3(file, "letter-hand-files");
                fileUrls.add(fileUrl);
            }
        }

        LetterMongo letterMongo = LetterMongo.builder()
                .senderId(user.getMemberID())
                .receiverId(letterHandReqDto.getReceiverId())
                .content(contentUrl)
                .files(fileUrls)
                .type("HandWritten")
                .build();

        letterMongoRepository.save(letterMongo);
    }

    // 수신자 확인 로직 추가 필요
    @Override
    @Transactional
    public void createTextLetter(String accessToken, LetterTextReqDto letterTextReqDto, MultipartFile content, List<MultipartFile> files) {
        UserDetailResDto user = accessTokenToMember(accessToken).getData();
        if (letterTextReqDto.getReceiverId().isEmpty()) {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_RECEIVER_ID);
        }

        if (letterTextReqDto.getText().trim().isEmpty()) {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_TEXT);
        }

        // text 금칙어 검사
        if (bannedWords.isBannedWords(letterTextReqDto.getText())) {
            throw new BadRequestException(ErrorCode.EXISTS_FORBIDDEN_WORD);
        }

        if (content.isEmpty()) {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_CONTENT);
        }
        String contentUrl = uploadFileToS3(content, "letter-text-content");

        List<String> fileUrls = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                String fileUrl = uploadFileToS3(file, "letter-hand-files");
                fileUrls.add(fileUrl);
            }
        }

        LetterMongo letterMongo = LetterMongo.builder()
                .senderId(user.getMemberID())
                .receiverId(letterTextReqDto.getReceiverId())
                .content(contentUrl)
                .files(fileUrls)
                .type("Digital")
                .build();

        letterMongoRepository.save(letterMongo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void placeLetter(String accessToken, LetterPlaceReqDto letterPlaceReqDto, List<MultipartFile> files) {
        LetterMongo letterMongo = letterMongoRepository.findById(letterPlaceReqDto.getId()).orElseThrow(() -> {
            return new BadRequestException(ErrorCode.NOT_EXISTS_LETTER);
        });

        // 편지 작성 유저와 요청을 보낸 유저가 같은 유저인지 확인
        UserDetailResDto user = accessTokenToMember(accessToken).getData();
        if (!letterMongo.getSenderId().equals(user.getMemberID())) {
            throw new BadRequestException(ErrorCode.NOT_EQUAL_SENDER);
        }

        Double lat = letterPlaceReqDto.getLat();
        Double lng = letterPlaceReqDto.getLng();
        if (lat == null || lng == null) {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_LAT_OR_LNG);
        }

        // 차단 확인(From(R) → To(S) 차단, DB 저장 x)
        List<FriendDetailResDto> friends = FriendIsBlocked(accessToken, letterMongo.getSenderId(), letterMongo.getReceiverId()).getData();
        for (FriendDetailResDto friend : friends) {
            if (friend.getFrom().equals(letterMongo.getReceiverId()) && friend.getTo().equals(letterMongo.getSenderId()) && friend.isBlocked()) {
                return;
            }
        }

        LetterMySQL letterMySQL = LetterMySQL.builder()
                .senderId(letterMongo.getSenderId())
                .receiverId(letterMongo.getReceiverId())
                .content(letterMongo.getContent())
                .type(letterMongo.getType())
                .lat(lat)
                .lng(lng)
                .build();

        if (files == null) {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_PLACE_IMAGES);
        }

        for (MultipartFile file :files) {
            if (file.isEmpty()) {
                throw new BadRequestException(ErrorCode.NOT_EXISTS_PLACE_IMAGES);
            }
        }

        LetterMySQL letter = letterJpaRepository.save(letterMySQL);

        // 편지 첨부 파일 저장
        if (!letterMongo.getFiles().isEmpty()) {
            for (String imageUrl : letterMongo.getFiles()) {
                LetterImage letterImage = LetterImage.builder()
                        .letter(letter)
                        .imagePath(imageUrl)
                        .build();

                letterImageRepository.save(letterImage);
            }
        }

        for (MultipartFile file : files) {

            String fileUrl = uploadFileToS3(file, "letter-place-files");

            PlaceImage placeImage = PlaceImage.builder()
                    .letter(letter)
                    .imagePath(fileUrl)
                    .build();

            placeImageRepository.save(placeImage);
        }
        // Receiver, FCM 알림 발송 추가 필요
        UserInfoDetailResDto receiver = findByUserId(letterMongo.getReceiverId());
        try {
            String message = receiver.getNickname() + "님이 당신에게 편지를 보냈습니다.";
            firebaseCloudMessageService.sendMessageTo(receiver.getFcmToken(), "뱁새가 편지를 물고 왔어요.",message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        letterMongoRepository.deleteById(letterMongo.getId());
    }

    // 첨부된 파일이 이미지 파일인지 확인
    private boolean isImageFile(String filename) {
        String extenstion = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        String[] allowedExtensions = {"png", "jpg", "jpeg", "heif", "hevc", "gif", "jfif"};

        for (String allowedExtension : allowedExtensions) {
            if (extenstion.equals(allowedExtension)) {
                return true;
            }
        }
        return false;
    }

    private String uploadFileToS3(MultipartFile file, String s3Folder) {
        try {
            String fileName = file.getOriginalFilename();
            if (isImageFile(fileName)) {
                return s3Uploader.uploadFiles(file, s3Folder);
            } else {
                throw new BadRequestException(ErrorCode.INVALID_IMAGE_FORMAT);
            }
        } catch (IOException e) {
            throw new BadRequestException(ErrorCode.FAIL_UPLOAD_FILE);
        }
    }

    @Override
    @Transactional
    public List<LetterSendResDto> getSendLetters(String accessToken) {
        UserDetailResDto sender = accessTokenToMember(accessToken).getData();

        List<LetterSendResDto> letters = letterJpaRepository.findBySenderId(sender.getMemberID())
                .stream()
                .map(letterMySQL -> {
                    String receiverNickname = findByUserId(letterMySQL.getReceiverId()).getNickname();
                    return new LetterSendResDto(letterMySQL, receiverNickname);
                })
                .collect(Collectors.toList());
        return letters;
    }

    @Override
    @Transactional
    public List<LetterUnsendResDto> getUnsendLetters(String accessToken) {
        String userId = accessTokenToMember(accessToken).getData().getMemberID();

        List<LetterUnsendResDto> letters = letterMongoRepository.findBySenderId(userId)
                .stream()
                .map(letterMongo -> {
                    String receiverNickname = findByUserId(letterMongo.getReceiverId()).getNickname();
                    return new LetterUnsendResDto(letterMongo, receiverNickname);
                })
                .collect(Collectors.toList());
        return letters;
    }

    // isPickup(true) & isRead(상관 x)
    @Override
    @Transactional
    public List<LetterPickUpResDto> getPickupLetters(String accessToken) {
        String userId = accessTokenToMember(accessToken).getData().getMemberID();

        List<LetterPickUpResDto> letters = letterJpaRepository.findByReceiverIdAndIsPickup(userId, true)
                .stream()
                .map(letterMySQL -> {
                    String senderNickname = findByUserId(letterMySQL.getSenderId()).getNickname();
                    return new LetterPickUpResDto(letterMySQL, senderNickname, letterMySQL.isRead());
                })
                .collect(Collectors.toList());
        return letters;
    }

    @Override
    @Transactional
    public List<LetterNotPickUpResDto> getNotPickupLetters(String accessToken) {
        String userId = accessTokenToMember(accessToken).getData().getMemberID();

        List<LetterNotPickUpResDto> letters = letterJpaRepository.findByReceiverIdAndIsPickup(userId, false)
                .stream()
                .map(letterMySQL -> {
                    String senderNickname = findByUserId(letterMySQL.getSenderId()).getNickname();
                    return new LetterNotPickUpResDto(letterMySQL, senderNickname, letterMySQL.getSenderId());
                })
                .collect(Collectors.toList());
        return letters;
    }

    @Override
    @Transactional
    public LetterReceivedDetailResDto getLetter(String accessToken, Long letter_id) {
        UserDetailResDto user = accessTokenToMember(accessToken).getData();

        LetterMySQL letterMySQL = letterJpaRepository.findById(letter_id).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_LETTER);
        });

        // SendId, ReceivedId가 해당 맴버인 것만 열람 가능
        if (!letterMySQL.getSenderId().equals(user.getMemberID()) && (!letterMySQL.getReceiverId().equals(user.getMemberID()))) {
            throw new BadRequestException(ErrorCode.NOT_EQUAL_SENDER_OR_RECEIVER);
        }

        String senderNickname = findByUserId(letterMySQL.getSenderId()).getNickname();
        String receiverNickname = findByUserId(letterMySQL.getReceiverId()).getNickname();

        /*
         isFriend 변수 추가
         해당 요청을 보낸 멤버가 ReceivedId와 일치하면 IS_READ = true로 변경
         */
        boolean flag = false;
        if (letterMySQL.getReceiverId().equals(user.getMemberID())) {
            letterJpaRepository.setLetterIsReadTrue(letterMySQL.getId());
            List<FriendDetailResDto> friends = FriendIsBlocked(accessToken, letterMySQL.getReceiverId(), letterMySQL.getSenderId()).getData();
            for (FriendDetailResDto friend : friends) {
                if (friend.getFrom().equals(letterMySQL.getReceiverId()) && friend.getTo().equals(letterMySQL.getSenderId())) {
                    flag = true;
                }
            }
        }


        if (letterMySQL.getSenderId().equals(user.getMemberID())) {
            flag = true;
        }

        LetterReceivedDetailResDto letter = new LetterReceivedDetailResDto(letterMySQL, senderNickname, letterMySQL.getSenderId(), receiverNickname, flag);
        return letter;
    }

    // accessToken으로 해당 member에 대한 정보 받기
    private UserResDto accessTokenToMember(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String apiUrl = "http://3.34.86.93/api/user";
        ResponseEntity<UserResDto> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, UserResDto.class);

        UserResDto res = responseEntity.getBody();

        return res;
    }

    // member_Id 2개를 보냈을 때 차단되있는지 안되어있는지 판별
    private FriendResDto FriendIsBlocked(String accessToken, String senderId, String receiverId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);

        FriendReqDto req = FriendReqDto.builder()
                .from(senderId)
                .to(receiverId)
                .build();

        HttpEntity<FriendReqDto> requestEntity = new HttpEntity<>(req, headers);

        String apiUrl = "http://3.34.86.93/api/friend";
        ResponseEntity<FriendResDto> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, FriendResDto.class);

        FriendResDto res = responseEntity.getBody();

        return res;
    }

    private UserInfoDetailResDto findByUserId(String userId) {
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "http://3.34.86.93/api/user/" + userId;
        ResponseEntity<UserInfoResDto> responseEntity = restTemplate.getForEntity(apiUrl, UserInfoResDto.class);

        UserInfoResDto res = responseEntity.getBody();

        return res.getData();
    }

    public void updateIsPickup(String accessToken, Long letter_id) {
        LetterMySQL letterMySQL = letterJpaRepository.findById(letter_id).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_LETTER);
        });
        letterJpaRepository.setLetterIsPickupTrue(letterMySQL.getId());
    }

    public void test(String accessToken) {
        UserDetailResDto user = accessTokenToMember(accessToken).getData();
        System.out.println(user.getFcmToken());

        String message = user.getNickname() + "님이 당신에게 편지를 보냈습니다.";
        try {
            firebaseCloudMessageService.sendMessageTo(user.getFcmToken(), "뱁새가 편지를 물고 왔어요.", message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}