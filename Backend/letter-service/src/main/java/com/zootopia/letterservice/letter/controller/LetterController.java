package com.zootopia.letterservice.letter.controller;

import com.zootopia.letterservice.common.dto.BaseResponseBody;
import com.zootopia.letterservice.letter.dto.request.LetterPlaceReqDto;
import com.zootopia.letterservice.letter.dto.response.LetterReceivedResDto;
import com.zootopia.letterservice.letter.dto.response.LetterSendResDto;
import com.zootopia.letterservice.letter.dto.response.LetterUnsendResDto;
import com.zootopia.letterservice.letter.entity.LetterMySQL;
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
import java.util.stream.Collectors;

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
            @ApiResponse(responseCode = "L-003", description =  "NOT_EXISTS_RECEIVER_ID", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-003\",\n \"message\": \"수신자 ID는 필수 항목 입니다.\"\n}")))
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
            @ApiResponse(responseCode = "L-005", description =  "EXISTS_FORBIDDEN_WORD", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-005\",\n \"message\": \"편지에 금칙어가 포함되어 있습니다. 금칙어를 제외하고 작성해주세요.\"\n}")))
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

    @Operation(summary = "편지 배치", description = "letterId(편지 ID, String), lat(편지 위도, Double), lng(편지 경도, Double), files(편지 배치 장소 이미지) s3, db에 저장\n\n " +
            "(letterId : 필수, lat : 필수, lng : 필수, files : 필수(1장 이상))")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =  "CREATED", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n \"status\": 201,\n \"message\": \"편지 생성 성공\"\n}"))),
            @ApiResponse(responseCode = "L-002", description =  "INVALID_IMAGE_FORMAT", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-002\",\n \"message\": \"지원하지 않는 이미지 파일 확장자입니다.\"\n}"))),
            @ApiResponse(responseCode = "L-006", description =  "NOT_EQUAL_USER", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-006\",\n \"message\": \"편지 작성자와 요청을 보낸 사용자가 일치하지 않습니다.\"\n}"))),
            @ApiResponse(responseCode = "L-007", description =  "NOT_EXISTS_LETTER", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-007\",\n \"message\": \"존재하지 않는 편지입니다.\"\n}"))),
            @ApiResponse(responseCode = "L-008", description =  "NOT_EXISTS_LAT_OR_LNG", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-008\",\n \"message\": \"위도, 경도는 필수 항목입니다.\"\n}"))),
            @ApiResponse(responseCode = "L-009", description =  "NOT_EXISTS_PLACE_IMAGES", content = @Content(examples = @ExampleObject(value = "{\n \"status\": \"L-009\",\n \"message\": \"배치 장소에 대한 이미지 파일은 필수 항목입니다.\"\n}"))),
    })
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

    @Operation(summary = "발송 편지 목록 조회", description = "Authorization : Bearer {accessToken}, 필수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "OK", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            " \"status\": 200," +
                            " \"message\": \"발송 편지 목록 조회 성공\"," +
                            " \"data\": [" +
                            "       {" +
                            "           \"index\": 1," +
                            "           \"receiver\": \"사용자 닉네임\"" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    @GetMapping("/placed")
    public ResponseEntity<? extends BaseResponseBody> getSendLetters(@RequestHeader(value = "Authorization", required = false) String accessToken) {
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "발송 편지 목록 조회 성공", letterService.getSendLetters()));
    }

    @Operation(summary = "미발송 편지 목록 조회", description = "Authorization : Bearer {accessToken}, 필수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "OK", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            " \"status\": 200," +
                            " \"message\": \"미발송 편지 목록 조회 성공\"," +
                            " \"data\": [" +
                            "       {" +
                            "           \"index\": 1," +
                            "           \"receiver\": \"사용자 닉네임\"" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    @GetMapping("/unplaced")
    public ResponseEntity<? extends BaseResponseBody> getUnsendLetters(@RequestHeader(value = "Authorization", required = false) String accessToken) {
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "미발송 편지 목록 조회 성공", letterService.getUnsendLetters()));
    }

    @Operation(summary = "열람한 수신 편지 목록 조회", description = "Authorization : Bearer {accessToken}, 필수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "OK", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            " \"status\": 200," +
                            " \"message\": \"열람한 편지 목록 조회 성공\"," +
                            " \"data\": [" +
                            "       {" +
                            "           \"index\": 1," +
                            "           \"sender\": \"사용자 닉네임\"," +
                            "           \"time\": \"yyyy-MM-ddThh:mm:ss.ssssss\"," +
                            "           \"lat\": 125.345436," +
                            "           \"lng\": 45.235233," +
                            "           \"location\":[" +
                            "                           \"https://zootopia-s3.s3.ap-northeast-2.amazonaws.com/-/file.jpg\"" +
                                                    "]" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    @GetMapping("/checked")
    public ResponseEntity<? extends BaseResponseBody> getReadLetters(@RequestHeader(value = "Authorization", required = false) String accessToken) {
        // accessToken으로 멤버 객체 찾기 → SendId가 해당 맴버인 것 중 isRead = true인 값들만 반환
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "열람한 편지 목록 조회 성공", letterService.getReadLetters()));
    }

    @Operation(summary = "미열람한 수신 편지 목록 조회", description = "Authorization : Bearer {accessToken}, 필수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "OK", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            " \"status\": 200," +
                            " \"message\": \"미열람한 편지 목록 조회 성공\"," +
                            " \"data\": [" +
                            "       {" +
                            "           \"index\": 1," +
                            "           \"sender\": \"사용자 닉네임\"," +
                            "           \"time\": \"yyyy-MM-ddThh:mm:ss.ssssss\"," +
                            "           \"lat\": 125.345436," +
                            "           \"lng\": 45.235233," +
                            "           \"location\":[" +
                            "                           \"https://zootopia-s3.s3.ap-northeast-2.amazonaws.com/-/file.jpg\"" +
                            "                       ]" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    @GetMapping("/unchecked")
    public ResponseEntity<? extends BaseResponseBody> getUnReadLetters(@RequestHeader(value = "Authorization", required = false) String accessToken) {
        // accessToken으로 멤버 객체 찾기 → SendId가 해당 맴버인 것 중 isRead = false인 값들만 반환
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "미열람한 편지 목록 조회 성공", letterService.getUnreadLetters()));
    }

    @Operation(summary = "편지 상세조회", description = "Authorization : Bearer {accessToken}, 필수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "OK", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            " \"status\": 200," +
                            " \"message\": \"편지 상세 조회 성공\"," +
                            " \"data\": [" +
                            "       {" +
                            "           \"index\": 1," +
                            "           \"content\": \"https://zootopia-s3.s3.ap-northeast-2.amazonaws.com/-/file.jpg\"," +
                            "           \"sender\": \"사용자 닉네임\"," +
                            "           \"receiver\": \"사용자 닉네임\"," +
                            "           \"time\": \"yyyy-MM-ddThh:mm:ss.ssssss\"," +
                            "           \"lat\": 125.345436," +
                            "           \"lng\": 45.235233," +
                            "           \"files\":[" +
                            "                           \"https://zootopia-s3.s3.ap-northeast-2.amazonaws.com/-/file.jpg\"" +
                            "                   ]," +
                            "           \"isFriend\": true" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    @GetMapping("/{letter_id}")
    public ResponseEntity<? extends BaseResponseBody> getLetter(@PathVariable Long letter_id,
                                                                @RequestHeader(value = "Authorization", required = false) String accessToken) {
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "편지 상세 조회 성공", letterService.getLetter(letter_id)));
    }
}
