package com.zootopia.letterservice.store.service;

import com.zootopia.letterservice.store.dto.request.CharacterBuyReqDto;
import com.zootopia.letterservice.store.dto.request.CrowTitReqDto;
import com.zootopia.letterservice.store.dto.request.LetterPaperBuyReqDto;
import com.zootopia.letterservice.store.dto.request.LetterPaperReqDto;
import com.zootopia.letterservice.store.entity.CrowTit;
import com.zootopia.letterservice.store.entity.LetterPaper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public interface StoreService {

    void buyLetterPaper(String memberId, LetterPaperBuyReqDto letterPaperBuyReqDto);

    LetterPaper getLetterPaperDetail(Long letterpaperId);

    void buyCharacter(String memberId, CharacterBuyReqDto characterBuyReqDto);

    CrowTit getCharacterInfo(Long charater_id);

    void upload(LetterPaperReqDto letterPaperReqDto, List<MultipartFile> files);

//    void upload(CrowTitReqDto crowTitReqDto, List<MultipartFile> files);

}
