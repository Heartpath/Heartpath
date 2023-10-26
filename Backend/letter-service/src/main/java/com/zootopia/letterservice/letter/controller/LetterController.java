package com.zootopia.letterservice.letter.controller;

import com.zootopia.letterservice.common.dto.BaseResponseBody;
import com.zootopia.letterservice.letter.dto.request.LetterHandReqDto;
import com.zootopia.letterservice.letter.dto.response.LetterOutDetailResDto;
import com.zootopia.letterservice.letter.service.LetterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Letter API", description = "HeartPath letter api")
public class LetterController {
    private final LetterService letterService;

    @Operation(summary = "수기 편지 생성", description = "content(편지 내용 파일), files(편지 첨부 파일 - 이미지 파일) s3, db에 저장\n\n " +
            "(content : 필수, files : 선택)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =  "CREATED", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n \"status\": 201,\n \"message\": \"편지 생성 성공\"\n}"))),
            @ApiResponse(responseCode = "L-001", description =  "NOT_EXISTS_CONTENT", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-001\",\n \"message\": \"편지 내용 파일은 필수 항목입니다.\"\n}"))),
            @ApiResponse(responseCode = "L-002", description =  "INVALID_IMAGE_FORMAT", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-002\",\n \"message\": \"지원하지 않는 이미지 파일 확장자입니다.\"\n}"))),
    })
    @PostMapping("/hand")
    public ResponseEntity<? extends BaseResponseBody> createHandLetter(@RequestHeader(value = "Authorization", required = false) String accessToken,
                                                                       @RequestPart(value = "content", required = true) MultipartFile content,
                                                                       @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        // 멤버 객체 찾기
        // LetterHandReqDto letterHandReqDto = new LetterHandReqDto();
        letterService.createHandLetter(content, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "편지 생성 성공"));
    }

    @PostMapping("/text")
    public ResponseEntity<? extends BaseResponseBody> createTextLetter(@RequestHeader(value = "Authorization", required = false) String accessToken,
                                                                       @RequestPart(value = "text", required = true) String text,
                                                                       @RequestPart(value = "content", required = true) MultipartFile content,
                                                                       @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        // 멤버 객체 찾기
        letterService.createTextLetter(text, content, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "편지 생성 성공"));
    }

}
