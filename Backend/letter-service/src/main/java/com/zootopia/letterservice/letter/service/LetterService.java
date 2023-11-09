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

    void createHandLetter (String accessToken, LetterHandReqDto letterHandReqDto, MultipartFile content, List<MultipartFile> files);

    void createTextLetter (String accessToken, LetterTextReqDto letterTextReqDto, MultipartFile content, List<MultipartFile> files);

    void placeLetter(String accessToken, LetterPlaceReqDto letterPlaceReqDto, List<MultipartFile> files);

    List<LetterSendResDto> getSendLetters(String accessToken);

    List<LetterUnsendResDto> getUnsendLetters(String accessToken);

    List<LetterReceivedResDto> getReadLetters(String accessToken);

    List<LetterReceivedResDto> getUnreadLetters(String accessToken);

    LetterReceivedDetailResDto getLetter(String accessToken, Long letter_id);
}
