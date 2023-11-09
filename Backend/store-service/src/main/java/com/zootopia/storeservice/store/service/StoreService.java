package com.zootopia.storeservice.store.service;

import com.zootopia.storeservice.store.dto.request.CharacterBuyReqDto;
import com.zootopia.storeservice.store.dto.request.LetterPaperBuyReqDto;
import com.zootopia.storeservice.store.dto.response.CrowTitResDto;
import com.zootopia.storeservice.store.dto.response.LetterPaperResDto;
import com.zootopia.storeservice.store.entity.CrowTit;
import com.zootopia.storeservice.store.entity.LetterPaper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface StoreService {

    void buyLetterPaper(String memberId, LetterPaperBuyReqDto letterPaperBuyReqDto);
    List<LetterPaperResDto> getLetterPaperAll(String memberId);


    LetterPaper getLetterPaperDetail(Long letterpaperId);

    void buyCrowTit(String memberId, CharacterBuyReqDto characterBuyReqDto);

    CrowTit getCrowTitInfo(Long charater_id);

    List<CrowTitResDto> getCrowTitListAll(String memberId);

//    void upload(CrowTitReqDto crowTitReqDto, List<MultipartFile> files);

//    void upload(CrowTitReqDto crowTitReqDto, List<MultipartFile> files);

}
