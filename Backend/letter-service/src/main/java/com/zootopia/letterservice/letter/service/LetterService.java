package com.zootopia.letterservice.letter.service;

import com.zootopia.letterservice.letter.dto.request.LetterHandReqDto;
import com.zootopia.letterservice.letter.dto.request.LetterPlaceReqDto;
import com.zootopia.letterservice.letter.dto.request.LetterTextReqDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface LetterService {

    // Member 추가해야 함.
//    void createHandLetter (LetterHandReqDto letterHandReqDto, MultipartFile content, List<MultipartFile> files);
    void createHandLetter (MultipartFile content, List<MultipartFile> files);

//    void createTextLetter (LetterTextReqDto letterTextReqDto, MultipartFile content, List<MultipartFile> files);
    void createTextLetter (String text, MultipartFile content, List<MultipartFile> files);

    void placeLetter(LetterPlaceReqDto letterPlaceReqDto, List<MultipartFile> files);

}
