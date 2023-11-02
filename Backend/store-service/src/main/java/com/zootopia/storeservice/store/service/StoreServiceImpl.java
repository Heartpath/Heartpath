package com.zootopia.storeservice.store.service;

import com.zootopia.storeservice.common.error.code.ErrorCode;
import com.zootopia.storeservice.common.error.exception.BadRequestException;
import com.zootopia.storeservice.common.s3.S3Uploader;
import com.zootopia.storeservice.store.dto.request.CharacterBuyReqDto;
import com.zootopia.storeservice.store.dto.request.CrowTitReqDto;
import com.zootopia.storeservice.store.dto.request.LetterPaperBuyReqDto;
import com.zootopia.storeservice.store.entity.CrowTit;
import com.zootopia.storeservice.store.entity.LetterPaper;
import com.zootopia.storeservice.store.entity.LetterPaperBook;
import com.zootopia.storeservice.store.repository.CrowTitRepository;
import com.zootopia.storeservice.store.repository.LetterPaperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    @Autowired
    private WebClient webClient;

    private final LetterPaperRepository letterPaperRepository;
    private final CrowTitRepository crowTitRepository;

    private final S3Uploader s3Uploader;

    public void buyLetterPaper(String memberId, LetterPaperBuyReqDto letterPaperBuyReqDto){
        LetterPaper letterPaper = letterPaperRepository.findById(letterPaperBuyReqDto.getLetterpaperId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_LETTERPAPER));

        LetterPaperBook letterPaperBook = LetterPaperBook.builder()
                .letterPaper(letterPaper)
                .acquisitionDate(LocalDateTime.now())
                .build();
    }

    public LetterPaper getLetterPaperDetail(Long letterpaperId){
        LetterPaper letterPaper = letterPaperRepository.findById(letterpaperId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_LETTERPAPER));
        return letterPaper;
    }

    public void buyCharacter(String memberId, CharacterBuyReqDto characterBuyReqDto){
        CrowTit crowTit = crowTitRepository.findById(characterBuyReqDto.getCharacterId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_CROWTIT));

        // ** 구매한 뱁새정보 멤버 서버로 전송

//        try {
//            ResponseEntity<String> result = webClient.post()
//                    .uri("/crowtit/save")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .bodyValue(crowTit)
//                    .retrieve()
//                    .toEntity(String.class)
//                    .block();
//            return;
//        }catch (Exception e){
//            throw new BadRequestException(ErrorCode.FAIL_SEND_TO_MEMBER_CROWTIT);
//        }
    }

    public CrowTit getCharacterInfo(Long charater_id){
        CrowTit crowTit = crowTitRepository.findById(charater_id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_CROWTIT));
        return crowTit;
    }

    public void upload(CrowTitReqDto crowTitReqDto, List<MultipartFile> files) {
//        if (letterPaperReqDto.getName().isEmpty() || letterPaperReqDto.getPrice() == null || letterPaperReqDto.getDescription().isEmpty() || letterPaperReqDto == null) {
//            throw new StoreBadRequestException(StoreErrorCode.FAIL);
//        }

        if (crowTitReqDto.getName().isEmpty() || crowTitReqDto.getPrice() == null || crowTitReqDto.getDescription().isEmpty() || crowTitReqDto == null) {
            throw new BadRequestException(ErrorCode.FAIL);
        }

        if (files != null) {
            for (MultipartFile file : files) {
                String fileUrl = uploadFileToS3(file, "crowtit");

//                LetterPaper letterPaper = LetterPaper.builder()
//                        .name(letterPaperReqDto.getName())
//                        .price(letterPaperReqDto.getPrice())
//                        .description(letterPaperReqDto.getDescription())
//                        .imagePath(fileUrl)
//                        .build();
//
//                letterPaperRepository.save(letterPaper);

                CrowTit crowTit = CrowTit.builder()
                        .name(crowTitReqDto.getName())
                        .price(Math.toIntExact(crowTitReqDto.getPrice()))
                        .description(crowTitReqDto.getDescription())
                        .imagePath(fileUrl)
                        .build();

                crowTitRepository.save(crowTit);
            }
        } else {
            throw new BadRequestException(ErrorCode.FAIL);
        }
    }

    private String uploadFileToS3(MultipartFile file, String s3Folder) {
        try {
            return s3Uploader.uploadFiles(file, s3Folder);
        } catch (IOException e) {
            throw new BadRequestException(ErrorCode.FAIL_UPLOAD_FILE);
        }
    }
}
