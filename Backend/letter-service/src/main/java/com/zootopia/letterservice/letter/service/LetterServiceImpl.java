package com.zootopia.letterservice.letter.service;

import com.zootopia.letterservice.common.error.code.ErrorCode;
import com.zootopia.letterservice.common.error.exception.BadRequestException;
import com.zootopia.letterservice.common.global.BannedWords;
import com.zootopia.letterservice.common.s3.S3Uploader;
import com.zootopia.letterservice.letter.dto.request.LetterPlaceReqDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class LetterServiceImpl implements LetterService {
    private final LetterJpaRepository letterJpaRepository;
    private final LetterMongoRepository letterMongoRepository;
    private final LetterImageRepository letterImageRepository;
    private final PlaceImageRepository placeImageRepository;
    private final BannedWords bannedWords;
    private final S3Uploader s3Uploader;

    // 수신자 확인 로직 추가 필요
    @Override
    @Transactional
    public void createHandLetter(MultipartFile content, List<MultipartFile> files) {
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
                .content(contentUrl)
                .files(fileUrls)
                .type("HandWritten")
                .build();

        letterMongoRepository.save(letterMongo);
    }

    // 수신자 확인 로직 추가 필요
    @Override
    @Transactional
    public void createTextLetter(String text, MultipartFile content, List<MultipartFile> files) {
        if (text.trim().isEmpty()) {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_TEXT);
        }

        // text 금칙어 검사
        if (bannedWords.isBannedWords(text)) {
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
                .content(contentUrl)
                .files(fileUrls)
                .type("Digital")
                .build();

        letterMongoRepository.save(letterMongo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void placeLetter(LetterPlaceReqDto letterPlaceReqDto, List<MultipartFile> files) {
        // accessToken으로 지금 요청을 보낸 유저랑 편지의 발신자랑 맞는지 확인하는 작업 필요함.
        LetterMongo letterMongo = letterMongoRepository.findById(letterPlaceReqDto.getId()).orElseThrow(() -> {
            return new BadRequestException(ErrorCode.NOT_EXISTS_LETTER);
        });

        Double lat = letterPlaceReqDto.getLat();
        Double lng = letterPlaceReqDto.getLng();
        if (lat == null || lng == null) {
            throw new BadRequestException(ErrorCode.NOT_EXISTS_LAT_OR_LNG);
        }

        LetterMySQL letterMySQL = LetterMySQL.builder()
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
        letterMongoRepository.delete(letterMongo);
    }

    // 첨부된 파일이 이미지 파일인지 확인
    private boolean isImageFile(String filename) {
        String extenstion = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        String[] allowedExtensions = {"png", "jpg", "jpeg", "heif", "hevc", "gif"};

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
    public List<LetterMySQL> getSendLetters() {
        // Member 객체 받아서 해당 멤버에 해당하는 편지만 가져와야 함.
//        return letterJpaRepository.findBySenderId();
        return letterJpaRepository.findAll();
    }

    @Override
    @Transactional
    public List<LetterMongo> getUnsendLetters() {
//        return letterMongoRepository.findBySenderId();
        return letterMongoRepository.findAll();
    }


}
