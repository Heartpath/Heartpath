package com.zootopia.letterservice.letter.service;

import com.zootopia.letterservice.letter.dto.request.LetterHandReqDto;
import com.zootopia.letterservice.letter.dto.request.LetterPlaceReqDto;
import com.zootopia.letterservice.letter.dto.request.LetterTextReqDto;
import com.zootopia.letterservice.letter.dto.response.*;
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

    List<LetterPickUpResDto> getPickupLetters(String accessToken);

    List<LetterNotPickUpResDto> getNotPickupLetters(String accessToken);

    LetterReceivedDetailResDto getLetter(String accessToken, Long letter_id);

    void updateIsPickup(String accessToken, Long letter_id);

    void test(String accessToken);
}
