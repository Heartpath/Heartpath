package com.zootopia.storeservice.store.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.zootopia.storeservice.common.error.code.ErrorCode;
import com.zootopia.storeservice.common.error.exception.BadRequestException;
//import com.zootopia.storeservice.common.s3.S3Uploader;
import com.zootopia.storeservice.store.dto.request.CharacterBuyReqDto;
import com.zootopia.storeservice.store.dto.request.CrowTitReqDto;
import com.zootopia.storeservice.store.dto.request.LetterPaperBuyReqDto;
import com.zootopia.storeservice.store.entity.*;
import com.zootopia.storeservice.store.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final LetterPaperRepository letterPaperRepository;
    private final LetterPaperBookRepository letterPaperBookRepository;
    private final CrowTitRepository crowTitRepository;
    private final CrowTitBookRepository crowTitBookRepository;
    private final PointRepository pointRepository;

//    private final S3Uploader s3Uploader;

    public void buyLetterPaper(String memberId, LetterPaperBuyReqDto letterPaperBuyReqDto){
        Optional<LetterPaper> letterPaperOptional = letterPaperRepository.findById(letterPaperBuyReqDto.getLetterpaperId());
        log.warn(String.valueOf(letterPaperBuyReqDto.getLetterpaperId()));
        log.warn(String.valueOf(letterPaperOptional));
        if (letterPaperOptional.isPresent()) {
            // "letterpaper"를 찾았을 때의 처리
            LetterPaper letterPaper = letterPaperOptional.get();
            List<Point> pointUsage = pointRepository.findByMemberId(memberId);
            // 포인트 사용 내역을 생성일자(`createdDate`)를 기준으로 내림차순으로 정렬
            pointUsage.sort(Comparator.comparing(Point::getCreatedDate).reversed());
            // 가장 최근의 포인트 사용 내역의 balance 가져오기
            int lastBalance = pointUsage.isEmpty() ? 0 : pointUsage.get(0).getBalance();
            int currentBalance = lastBalance + letterPaper.getPrice();

            LetterPaperBook letterPaperBook = LetterPaperBook.builder()
                    .letterPaper(letterPaper)
                    .memberId(memberId)
                    .acquisitionDate(LocalDateTime.now())
                    .build();
            letterPaperBookRepository.save(letterPaperBook);

            Point point = Point.builder()
                    .memberId(memberId)
                    .outline("편지지 구매")
                    .price(letterPaper.getPrice())
                    .balance(currentBalance)
                    .createdDate(LocalDateTime.now())
                    .build();
            pointRepository.save(point);
        } else {
            // "letterpaper"를 찾지 못했을 때의 처리 또는 예외를 던집니다.
            throw new BadRequestException(ErrorCode.NOT_EXISTS_LETTERPAPER);
        }

    }

    public LetterPaper getLetterPaperDetail(Long letterpaperId){
        LetterPaper letterPaper = letterPaperRepository.findById(letterpaperId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_LETTERPAPER));
        return letterPaper;
    }

    public void buyCharacter(String memberId, CharacterBuyReqDto characterBuyReqDto){
        CrowTit crowTit = crowTitRepository.findById(characterBuyReqDto.getCrowtitId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_CROWTIT));

        log.warn(String.valueOf(crowTit));

        List<Point> pointUsage = pointRepository.findByMemberId(memberId);
        // 포인트 사용 내역을 생성일자(`createdDate`)를 기준으로 내림차순으로 정렬
        pointUsage.sort(Comparator.comparing(Point::getCreatedDate).reversed());
        // 가장 최근의 포인트 사용 내역의 balance 가져오기
        int lastBalance = pointUsage.isEmpty() ? 0 : pointUsage.get(0).getBalance();
        int currentBalance = lastBalance+crowTit.getPrice();

        CrowTitBook crowTitBook = CrowTitBook.builder()
                .crowTit(crowTit)
                .memberId(memberId)
                .isMain(false)
                .acquisitionDate(LocalDateTime.now())
                .build();
        crowTitBookRepository.save(crowTitBook);

        Point point = Point.builder()
                .memberId(memberId)
                .outline("뱁새 구매")
                .price(crowTit.getPrice())
                .balance(currentBalance)
                .createdDate(LocalDateTime.now())
                .build();
        pointRepository.save(point);

    }

    public CrowTit getCharacterInfo(Long charater_id){
        CrowTit crowTit = crowTitRepository.findById(charater_id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_CROWTIT));
        return crowTit;
    }

//    public void upload(CrowTitReqDto crowTitReqDto, List<MultipartFile> files) {
////        if (letterPaperReqDto.getName().isEmpty() || letterPaperReqDto.getPrice() == null || letterPaperReqDto.getDescription().isEmpty() || letterPaperReqDto == null) {
////            throw new StoreBadRequestException(StoreErrorCode.FAIL);
////        }
//
//        if (crowTitReqDto.getName().isEmpty() || crowTitReqDto.getPrice() == null || crowTitReqDto.getDescription().isEmpty() || crowTitReqDto == null) {
//            throw new BadRequestException(ErrorCode.FAIL);
//        }
//
//        if (files != null) {
//            for (MultipartFile file : files) {
//                String fileUrl = uploadFileToS3(file, "crowtit");
//
////                LetterPaper letterPaper = LetterPaper.builder()
////                        .name(letterPaperReqDto.getName())
////                        .price(letterPaperReqDto.getPrice())
////                        .description(letterPaperReqDto.getDescription())
////                        .imagePath(fileUrl)
////                        .build();
////
////                letterPaperRepository.save(letterPaper);
//
//                CrowTit crowTit = CrowTit.builder()
//                        .name(crowTitReqDto.getName())
//                        .price(Math.toIntExact(crowTitReqDto.getPrice()))
//                        .description(crowTitReqDto.getDescription())
//                        .imagePath(fileUrl)
//                        .build();
//
//                crowTitRepository.save(crowTit);
//            }
//        } else {
//            throw new BadRequestException(ErrorCode.FAIL);
//        }
//    }
//
//    private String uploadFileToS3(MultipartFile file, String s3Folder) {
//        try {
//            return s3Uploader.uploadFiles(file, s3Folder);
//        } catch (IOException e) {
//            throw new BadRequestException(ErrorCode.FAIL_UPLOAD_FILE);
//        }
//    }
}
