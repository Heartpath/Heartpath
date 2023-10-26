package com.zootopia.letterservice.letter.controller;

import com.zootopia.letterservice.common.dto.BaseResponseBody;
import com.zootopia.letterservice.letter.dto.request.LetterHandReqDto;
import com.zootopia.letterservice.letter.service.LetterService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/letter")
public class LetterController {
    private final LetterService letterService;

    @PostMapping("/hand")
    public ResponseEntity<? extends BaseResponseBody> createHandLetter(@RequestHeader(value = "Authorization", required = false) String accessToken,
                                                                       @RequestPart(value = "content", required = true) MultipartFile content,
                                                                       @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        // 멤버 객체 찾기

        // LetterHandReqDto letterHandReqDto = new LetterHandReqDto();
        letterService.createHandLetter(content, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "편지 생성 성공"));
    }
}
