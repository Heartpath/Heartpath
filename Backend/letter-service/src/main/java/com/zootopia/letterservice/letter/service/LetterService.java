package com.zootopia.letterservice.letter.service;

import com.zootopia.letterservice.letter.dto.request.LetterHandReqDto;
import com.zootopia.letterservice.letter.dto.request.LetterPlaceReqDto;
import com.zootopia.letterservice.letter.dto.request.LetterTextReqDto;
import com.zootopia.letterservice.letter.dto.response.LetterReceivedDetailResDto;
import com.zootopia.letterservice.letter.dto.response.LetterReceivedResDto;
import com.zootopia.letterservice.letter.dto.response.LetterSendResDto;
import com.zootopia.letterservice.letter.dto.response.LetterUnsendResDto;
import com.zootopia.letterservice.letter.entity.LetterMongo;
import com.zootopia.letterservice.letter.entity.LetterMySQL;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface LetterService {

    // Member 추가해야 함.
//    void createHandLetter (LetterHandReqDto letterHandReqDto, MultipartFile content, List<MultipartFile> files);
    void createHandLetter (LetterHandReqDto letterHandReqDto, MultipartFile content, List<MultipartFile> files);

    void createTextLetter (LetterTextReqDto letterTextReqDto, MultipartFile content, List<MultipartFile> files);

    void placeLetter(LetterPlaceReqDto letterPlaceReqDto, List<MultipartFile> files);

    List<LetterSendResDto> getSendLetters();

    List<LetterUnsendResDto> getUnsendLetters();

    List<LetterReceivedResDto> getReadLetters();

    List<LetterReceivedResDto> getUnreadLetters();

    LetterReceivedDetailResDto getLetter(Long letter_id);
}
