package com.zootopia.letterservice.letter.controller;

import com.zootopia.letterservice.common.dto.BaseResponseBody;
import com.zootopia.letterservice.letter.dto.request.LetterPlaceReqDto;
import com.zootopia.letterservice.letter.service.LetterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

    @Operation(summary = "수기 편지 생성", description = "receiverId(수신자 ID), content(편지 내용 파일), files(편지 첨부 파일 - 이미지 파일) s3, db에 저장\n\n " +
            "(receiverId : 필수, content : 필수, files : 선택)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =  "CREATED", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n \"status\": 201,\n \"message\": \"편지 생성 성공\"\n}"))),
            @ApiResponse(responseCode = "L-001", description =  "NOT_EXISTS_CONTENT", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-001\",\n \"message\": \"편지 내용 파일은 필수 항목입니다.\"\n}"))),
            @ApiResponse(responseCode = "L-002", description =  "INVALID_IMAGE_FORMAT", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-002\",\n \"message\": \"지원하지 않는 이미지 파일 확장자입니다.\"\n}"))),
            @ApiResponse(responseCode = "L-003", description =  "NOT_EXISTS_RECEIVER_ID", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-003\",\n \"message\": \"수신자 ID는 필수 항목 입니다.\"\n}"))),
    })
    @PostMapping("/hand")
    public ResponseEntity<? extends BaseResponseBody> createHandLetter(@RequestHeader(value = "Authorization", required = false) String accessToken,
//                                                                       @RequestPart(value = "receiverId") String receiverId,
                                                                       @RequestPart(value = "content") MultipartFile content,
                                                                       @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        // accessToken → 발신자, receiverId → 수신자 멤버 객체 찾아서 service 넘기기
        // LetterHandReqDto letterHandReqDto = new LetterHandReqDto();
        letterService.createHandLetter(content, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "편지 생성 성공"));
    }

    @Operation(summary = "텍스트 편지 생성", description = "receiverId(수신자 ID), text(사용자 입력 텍스트), content(편지 내용 파일), files(편지 첨부 파일 - 이미지 파일) s3, db에 저장\n\n " +
            "(receiverId : 필수, text : 필수, content : 필수, files : 선택)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =  "CREATED", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n \"status\": 201,\n \"message\": \"편지 생성 성공\"\n}"))),
            @ApiResponse(responseCode = "L-001", description =  "NOT_EXISTS_CONTENT", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-001\",\n \"message\": \"편지 내용 파일은 필수 항목입니다.\"\n}"))),
            @ApiResponse(responseCode = "L-002", description =  "INVALID_IMAGE_FORMAT", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-002\",\n \"message\": \"지원하지 않는 이미지 파일 확장자입니다.\"\n}"))),
            @ApiResponse(responseCode = "L-003", description =  "NOT_EXISTS_RECEIVER_ID", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-003\",\n \"message\": \"수신자 ID는 필수 항목 입니다.\"\n}"))),
            @ApiResponse(responseCode = "L-004", description =  "NOT_EXISTS_TEXT", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-004\",\n \"message\": \"텍스트 편지 생성시 사용자 입력 텍스트는 필수 항목입니다.\"\n}"))),
            @ApiResponse(responseCode = "L-005", description =  "EXISTS_FORBIDDEN_WORD", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-005\",\n \"message\": \"편지에 금칙어가 포함되어 있습니다. 금칙어를 제외하고 작성해주세요.\"\n}"))),
    })
    @PostMapping("/text")
    public ResponseEntity<? extends BaseResponseBody> createTextLetter(@RequestHeader(value = "Authorization", required = false) String accessToken,
//                                                                       @RequestPart(value = "receiverId") String receiverId,
                                                                       @RequestPart(value = "text") String text,
                                                                       @RequestPart(value = "content") MultipartFile content,
                                                                       @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        // accessToken → 발신자, receiverId → 수신자 멤버 객체 찾아서 service 넘기기
        letterService.createTextLetter(text, content, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "편지 생성 성공"));
    }

    @PostMapping("/placed")
    public ResponseEntity<? extends BaseResponseBody> placeLetter(@RequestHeader(value = "Authorization", required = false) String accessToken,
                                                                  @RequestPart(value = "letterId") String letterId,
                                                                  @RequestPart(value = "lat", required = false) Double lat,
                                                                  @RequestPart(value = "lng", required = false) Double lng,
                                                                  @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        LetterPlaceReqDto letterPlaceReqDto = LetterPlaceReqDto.builder()
                .id(letterId)
                .lat(lat)
                .lng(lng)
                .build();

        letterService.placeLetter(letterPlaceReqDto, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "편지 배치 성공"));
    }
}
