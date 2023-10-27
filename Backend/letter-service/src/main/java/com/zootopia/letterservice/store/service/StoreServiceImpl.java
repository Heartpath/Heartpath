package com.zootopia.letterservice.store.service;

import com.zootopia.letterservice.common.error.code.ErrorCode;
import com.zootopia.letterservice.common.error.exception.BadRequestException;
import com.zootopia.letterservice.store.dto.request.LetterPaperBuyReqDto;
import com.zootopia.letterservice.store.entity.CrowTit;
import com.zootopia.letterservice.store.entity.LetterPaper;
import com.zootopia.letterservice.store.entity.LetterPaperBook;
import com.zootopia.letterservice.store.repository.CrowTitRepository;
import com.zootopia.letterservice.store.repository.LetterPaperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final LetterPaperRepository letterPaperRepository;
    private final CrowTitRepository crowTitRepository;

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

    public CrowTit getCharacterInfo(Long charater_id){
        CrowTit crowTit = crowTitRepository.findById(charater_id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_CROWTIT));
        return crowTit;
    }
}
