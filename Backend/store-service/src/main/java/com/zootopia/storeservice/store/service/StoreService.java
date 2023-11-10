package com.zootopia.storeservice.store.service;

import com.zootopia.storeservice.store.dto.request.CrowTitReqDto;
import com.zootopia.storeservice.store.dto.request.LetterPaperReqDto;
import com.zootopia.storeservice.store.dto.response.CrowTitResDto;
import com.zootopia.storeservice.store.dto.response.LetterPaperResDto;
import com.zootopia.storeservice.store.entity.CrowTit;
import com.zootopia.storeservice.store.entity.LetterPaper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface StoreService {

    void buyLetterPaper(String memberId, LetterPaperReqDto letterPaperBuyReqDto);
    List<LetterPaperResDto> getLetterPaperAll(String memberId);


    LetterPaper getLetterPaperDetail(int letterpaperId);

    void buyCrowTit(String memberId, CrowTitReqDto crowTitReqDto);
    void changeMainCrowTit(String memberId, CrowTitReqDto crowTitChangeReqDto);

    CrowTit getCrowTitInfo(int crowTitId);

    List<CrowTitResDto> getCrowTitListAll(String memberId);

//    void upload(CrowTitReqDto crowTitReqDto, List<MultipartFile> files);

//    void upload(CrowTitReqDto crowTitReqDto, List<MultipartFile> files);

}
