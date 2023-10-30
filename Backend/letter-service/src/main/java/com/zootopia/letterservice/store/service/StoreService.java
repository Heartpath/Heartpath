package com.zootopia.letterservice.store.service;

import com.zootopia.letterservice.store.dto.request.CharacterBuyReqDto;
import com.zootopia.letterservice.store.dto.request.LetterPaperBuyReqDto;
import com.zootopia.letterservice.store.entity.CrowTit;
import com.zootopia.letterservice.store.entity.LetterPaper;
import org.springframework.stereotype.Service;


@Service
public interface StoreService {

    void buyLetterPaper(String memberId, LetterPaperBuyReqDto letterPaperBuyReqDto);

    LetterPaper getLetterPaperDetail(Long letterpaperId);

    void buyCharacter(String memberId, CharacterBuyReqDto characterBuyReqDto);

    CrowTit getCharacterInfo(Long charater_id);
}
