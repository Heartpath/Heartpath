package com.zootopia.storeservice.store.service;

import com.zootopia.storeservice.store.entity.CrowTitBook;
import com.zootopia.storeservice.store.entity.LetterPaperBook;
import com.zootopia.storeservice.store.repository.CrowTitBookRepository;
import com.zootopia.storeservice.store.repository.LetterPaperBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final CrowTitBookRepository crowTitBookRepository;
    private final LetterPaperBookRepository letterPaperBookRepository;

    public void setDefaultCrowTit(String memberId){
        CrowTitBook crowTitBook = CrowTitBook.builder()
                .crowTitId(10)
                .memberId(memberId)
                .isMain(true)
                .acquisitionDate(LocalDateTime.now())
                .build();
        crowTitBookRepository.save(crowTitBook);
    }

    public void setDefaultLetterPaper(String memberId){
        LetterPaperBook letterPaperBook = LetterPaperBook.builder()
                .letterPaperId(1)
                .memberId(memberId)
                .acquisitionDate(LocalDateTime.now())
                .build();
        letterPaperBookRepository.save(letterPaperBook);
    }
}
