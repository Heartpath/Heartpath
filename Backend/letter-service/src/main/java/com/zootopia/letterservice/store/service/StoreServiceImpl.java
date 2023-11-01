package com.zootopia.letterservice.store.service;

import com.zootopia.letterservice.common.error.code.LetterErrorCode;
import com.zootopia.letterservice.common.error.code.StoreErrorCode;
import com.zootopia.letterservice.common.error.exception.LetterBadRequestException;
import com.zootopia.letterservice.common.error.exception.StoreBadRequestException;
import com.zootopia.letterservice.common.s3.S3Uploader;
import com.zootopia.letterservice.store.dto.request.CharacterBuyReqDto;
import com.zootopia.letterservice.store.dto.request.CrowTitReqDto;
import com.zootopia.letterservice.store.dto.request.LetterPaperBuyReqDto;
import com.zootopia.letterservice.store.dto.request.LetterPaperReqDto;
import com.zootopia.letterservice.store.entity.CrowTit;
import com.zootopia.letterservice.store.entity.LetterPaper;
import com.zootopia.letterservice.store.entity.LetterPaperBook;
import com.zootopia.letterservice.store.repository.CrowTitRepository;
import com.zootopia.letterservice.store.repository.LetterPaperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
                .orElseThrow(() -> new StoreBadRequestException(StoreErrorCode.NOT_EXISTS_LETTERPAPER));

        LetterPaperBook letterPaperBook = LetterPaperBook.builder()
                .letterPaper(letterPaper)
                .acquisitionDate(LocalDateTime.now())
                .build();
    }

    public LetterPaper getLetterPaperDetail(Long letterpaperId){
        LetterPaper letterPaper = letterPaperRepository.findById(letterpaperId)
                .orElseThrow(() -> new StoreBadRequestException(StoreErrorCode.NOT_EXISTS_LETTERPAPER));
        return letterPaper;
    }

    public void buyCharacter(String memberId, CharacterBuyReqDto characterBuyReqDto){
        CrowTit crowTit = crowTitRepository.findById(characterBuyReqDto.getCharacterId())
                .orElseThrow(() -> new StoreBadRequestException(StoreErrorCode.NOT_EXISTS_CROWTIT));

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
                .orElseThrow(() -> new StoreBadRequestException(StoreErrorCode.NOT_EXISTS_CROWTIT));
        return crowTit;
    }

    public void upload(CrowTitReqDto crowTitReqDto, List<MultipartFile> files) {
//        if (letterPaperReqDto.getName().isEmpty() || letterPaperReqDto.getPrice() == null || letterPaperReqDto.getDescription().isEmpty() || letterPaperReqDto == null) {
//            throw new StoreBadRequestException(StoreErrorCode.FAIL);
//        }

        if (crowTitReqDto.getName().isEmpty() || crowTitReqDto.getPrice() == null || crowTitReqDto.getDescription().isEmpty() || crowTitReqDto == null) {
            throw new StoreBadRequestException(StoreErrorCode.FAIL);
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
                        .price(crowTitReqDto.getPrice())
                        .description(crowTitReqDto.getDescription())
                        .imagePath(fileUrl)
                        .build();

                crowTitRepository.save(crowTit);
            }
        } else {
            throw new StoreBadRequestException(StoreErrorCode.FAIL);
        }
    }

    private String uploadFileToS3(MultipartFile file, String s3Folder) {
        try {
            return s3Uploader.uploadFiles(file, s3Folder);
        } catch (IOException e) {
            throw new LetterBadRequestException(LetterErrorCode.FAIL_UPLOAD_FILE);
        }
    }
}
