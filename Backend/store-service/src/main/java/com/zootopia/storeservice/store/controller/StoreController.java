package com.zootopia.storeservice.store.controller;

import com.zootopia.storeservice.common.dto.BaseResponseBody;
import com.zootopia.storeservice.store.dto.request.CharacterBuyReqDto;
import com.zootopia.storeservice.store.dto.request.CrowTitReqDto;
import com.zootopia.storeservice.store.dto.request.LetterPaperBuyReqDto;
import com.zootopia.storeservice.store.entity.CrowTit;
import com.zootopia.storeservice.store.entity.LetterPaper;
import com.zootopia.storeservice.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store")
public class StoreController {

    @Autowired
    private WebClient webClient;

    private final StoreService storeService;

    // 편지지 목록 조회
    @GetMapping("/letterpaper")
    @Operation(summary = "편지지 목록 조회", description = "Authorization : Bearer {accessToken}, 필수\n\n " +
                                                        "isowned : 사용자가 가지고 있는 상품인지 아닌지 판단하는 boolean")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "OK", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            " \"status\": 200," +
                            " \"message\": \"편지지 목록 조회 성공\"," +
                            " \"data\": [" +
                            "       {" +
                            "           \"index\": 1," +
                            "           \"name\": \"빨간 편지지\"," +
                            "           \"price\": 300," +
                            "           \"image\": \"url\"," +
                            "           \"isowned\": \"true\"" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    public ResponseEntity<? extends BaseResponseBody> getLetterPaperList(@RequestHeader("Authorization") String accessToken){

//        String memberId = webClient
//                .post()
//                .header("Authorization", accessToken)
//                .retrieve()
//                .bodyToMono(Object.class.getId());

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "편지지 목록 조회 성공"));
    }

    // 편지지 상세 조회
    @GetMapping("/letterpaper/{letterpaper_id}")
    @Operation(summary = "편지지 상세 조회", description = "Authorization : Bearer {accessToken}, 필수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "OK", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            " \"status\": 200," +
                            " \"message\": \"편지지 상세 조회 성공\"," +
                            " \"data\": [" +
                            "       {" +
                            "           \"index\": 1," +
                            "           \"name\": \"빨간 편지지\"," +
                            "           \"description\": \"예쁜 빨간색 물방울 무늬를 가진 편지지이다.\"," +
                            "           \"price\": 300," +
                            "           \"image\": \"url\"" +
                            "       }" +
                            "   ]" +
                            "}"))),
            @ApiResponse(responseCode = "4001", description =  "NOT_EXISTS_LETTERPAPER", content = @Content(examples = @ExampleObject(value = "{\n \"httpStatus\": \"400 BAD_REQUEST\",\n \"status\": 4001,\n \"message\": \"존재하지 않는 편지지입니다.\"\n}"))),
    })
    public ResponseEntity<? extends BaseResponseBody> getLetterPaper(@RequestHeader("Authorization") String accessToken,
                                                                     @PathVariable(value = "letterpaper_id") Long letterpaper_id){

        LetterPaper letterPaperInfo = storeService.getLetterPaperDetail(letterpaper_id);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "편지지 상세 조회 성공", letterPaperInfo));
    }

    // 편지지 구매
    @PostMapping("/letterpaper/buy")
    @Operation(summary = "편지지 구매", description = "Authorization : Bearer {accessToken}, 필수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =  "CREATED", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n \"status\": 201,\n \"message\": \"편지지 구매 성공\"\n}"))),
            @ApiResponse(responseCode = "4001", description =  "NOT_EXISTS_LETTERPAPER", content = @Content(examples = @ExampleObject(value = "{\n \"httpStatus\": \"400 BAD_REQUEST\",\n \"status\": 4001,\n \"message\": \"존재하지 않는 편지지입니다.\"\n}")))}
    )
    public ResponseEntity<? extends BaseResponseBody> buyLetterPaper(@RequestHeader("Authorization") String accessToken,
                                                                     @RequestBody LetterPaperBuyReqDto letterPaperBuyReqDto){

//        String memberId = webClient
//            .post()
//            .header("Authorization", accessToken)
//            .retrieve()
//            .bodyToMono(Object.class.getId());
//
//        storeService.buyLetterPaper(memberId, letterPaperBuyReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "편지 구매 성공"));
    }


    // ########################################################


    // 캐릭터 목록 조회
    @GetMapping("/character")
    @Operation(summary = "캐릭터 목록 조회", description = "Authorization : Bearer {accessToken}, 필수\n\n " +
                                                        "isowned : 사용자가 가지고 있는 캐릭터인지 아닌지 판단하는 boolean")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "OK", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            " \"status\": 200," +
                            " \"message\": \"캐릭터 목록 조회 성공\"," +
                            " \"data\": [" +
                            "       {" +
                            "           \"index\": 1," +
                            "           \"name\": \"일반 뱁새\"," +
                            "           \"price\": 100," +
                            "           \"image\": \"url\"," +
                            "           \"isowned\": \"true\"" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    public ResponseEntity<? extends BaseResponseBody> getCharacterList(@RequestHeader("Authorization") String accessToken){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "캐릭터 목록 조회 성공"));
    }

    // 캐릭터 상세 조회
    @GetMapping("/character/{charater_id}")
    @Operation(summary = "캐릭터 상세 조회", description = "Authorization : Bearer {accessToken}, 필수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "OK", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            " \"status\": 200," +
                            " \"message\": \"캐릭터 상세 조회 성공\"," +
                            " \"data\": [" +
                            "       {" +
                            "           \"index\": 1," +
                            "           \"name\": \"일반 뱁새\"," +
                            "           \"description\": \"편지 배달을 업으로 삼는 뱁새다. 몹시 지친 직장인과 유사한 퀭한 생김새를 가지고 있다.\"," +
                            "           \"price\": 300," +
                            "           \"image\": \"url\"" +
                            "       }" +
                            "   ]" +
                            "}"))),
            @ApiResponse(responseCode = "4001", description =  "NOT_EXISTS_CROWTIT", content = @Content(examples = @ExampleObject(value = "{\n \"httpStatus\": \"400 BAD_REQUEST\",\n \"status\": 4001,\n \"message\": \"존재하지 않는 뱁새입니다.\"\n}"))),
})
    public ResponseEntity<? extends BaseResponseBody> getCharacter(@RequestHeader("Authorization") String accessToken,
                                                                   @PathVariable("charater_id") Long charater_id){

        CrowTit crowTitInfo = storeService.getCharacterInfo(charater_id);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "캐릭터 상세 조회 성공", crowTitInfo));
    }

    // 캐릭터 구매
    @PostMapping("/charater/buy")
    @Operation(summary = "캐릭터 구매", description = "Authorization : Bearer {accessToken}, 필수\\n\\n " +
            "characterBuyReqDto : { character_id : int }")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =  "CREATED", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n \"status\": 201,\n \"message\": \"캐릭터 구매 성공\"\n}"))),
            @ApiResponse(responseCode = "4001", description =  "NOT_EXISTS_CROWTIT", content = @Content(examples = @ExampleObject(value = "{\n \"httpStatus\": \"400 BAD_REQUEST\",\n \"status\": 4001,\n \"message\": \"존재하지 않는 뱁새입니다.\"\n}"))),
            @ApiResponse(responseCode = "4002", description = "FAIL_SEND_TO_MEMBER_CROWTIT", content = @Content(examples = @ExampleObject(value = "{\n \"httpStatus\": \"500 INTERNAL_SERVER_ERROR\", \n \"status\": \"4002\",\n \"message\": \"멤버서버로 뱁새 정보 전송에 실패하였습니다.\"\n}")))}
    )
    public ResponseEntity<? extends BaseResponseBody> buyCharacter(@RequestHeader("Authorization") String accessToken,
                                                                   @RequestBody CharacterBuyReqDto characterBuyReqDto){

//        String memberId = webClient
//            .post()
//            .header("Authorization", accessToken)
//            .retrieve()
//            .bodyToMono(Object.class.getId());
//
//        storeService.buyCharacter(memberId, characterBuyReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "캐릭터 구매 성공"));
    }

    @PostMapping("/upload")
    @Operation(summary = "캐릭터 및 편지지 S3 업로드용")
    public ResponseEntity<? extends BaseResponseBody> upload(@RequestPart(value = "name") String name,
                                                                           @RequestPart(value = "price") Long price,
                                                                           @RequestPart(value = "description") String description,
                                                                           @RequestPart List<MultipartFile> files) {
//        LetterPaperReqDto letterPaperReqDto = LetterPaperReqDto.builder()
//                .name(name)
//                .price(price)
//                .description(description)
//                .build();

        CrowTitReqDto crowTitReqDto = CrowTitReqDto.builder()
                .name(name)
                .price(price)
                .description(description)
                .build();

        storeService.upload(crowTitReqDto, files);

//        storeService.upload(crowTitReqDto, files);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "s3 이미지 업로드 성공"));
    }

}
