package com.zootopia.storeservice.store.controller;

import com.zootopia.storeservice.common.dto.BaseResponseBody;
import com.zootopia.storeservice.store.dto.request.CrowTitReqDto;
import com.zootopia.storeservice.store.dto.request.LetterPaperReqDto;
import com.zootopia.storeservice.store.dto.response.CrowTitResDto;
import com.zootopia.storeservice.store.dto.response.LetterPaperResDto;
import com.zootopia.storeservice.store.dto.response.UserResDto;
import com.zootopia.storeservice.store.entity.CrowTit;
import com.zootopia.storeservice.store.entity.CrowTitBook;
import com.zootopia.storeservice.store.entity.LetterPaper;
import com.zootopia.storeservice.store.repository.CrowTitBookRepository;
import com.zootopia.storeservice.store.service.MemberService;
import com.zootopia.storeservice.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store")
public class StoreController {

    private final StoreService storeService;
    private final MemberService memberService;
    private final CrowTitBookRepository crowTitBookRepository;


    @GetMapping("/health_check")
    public String checkServer(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();

        return String.format("200 OK to %s", remoteAddr);
    }

    // 편지지 목록 조회
    @GetMapping("/letterpaper")
    @Operation(summary = "편지지 목록 조회 (편지 작성용)", description = "Authorization : Bearer {accessToken}, 필수\n\n"
                                                                    +"사용자가 가지고 있는 편지지 목록을 조회합니다.")
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
                            "           \"imagePath\": \"url\"," +
                            "           \"isowned\": \"true\"" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    public ResponseEntity<? extends BaseResponseBody> getLetterPaperList(@RequestHeader("Authorization") String accessToken){

        UserResDto userResDto = memberService.accessTokenToMember(accessToken);
        String memberId = userResDto.getData().getMemberID();

        List<LetterPaperResDto> letterPaperBookList = storeService.getLetterPaper(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "편지지 목록 조회 성공", letterPaperBookList));
    }

    @GetMapping("/letterpaper/all")
    @Operation(summary = "편지지 전체 목록 조회 (상점용)", description = "Authorization : Bearer {accessToken}, 필수\n\n " +
                                                                    "isowned : 사용자가 가지고 있는 상품인지 아닌지 판단하는 boolean\n\n" +
                                                                    "편지지를 구매하는 페이지에 사용하는 API입니다.")
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
                            "           \"imagePath\": \"url\"," +
                            "           \"isowned\": \"true\"" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    public ResponseEntity<? extends BaseResponseBody> getLetterPaperListAll(@RequestHeader("Authorization") String accessToken){

        UserResDto userResDto = memberService.accessTokenToMember(accessToken);
        String memberId = userResDto.getData().getMemberID();

        List<LetterPaperResDto> letterPaperBookList = storeService.getLetterPaperAll(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "편지지 목록 조회 성공", letterPaperBookList));
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
                            "           \"imagePath\": \"url\"" +
                            "       }" +
                            "   ]" +
                            "}"))),
            @ApiResponse(responseCode = "4001", description =  "NOT_EXISTS_LETTERPAPER", content = @Content(examples = @ExampleObject(value = "{\n \"httpStatus\": \"400 BAD_REQUEST\",\n \"status\": 4001,\n \"message\": \"존재하지 않는 편지지입니다.\"\n}"))),
    })
    public ResponseEntity<? extends BaseResponseBody> getLetterPaper(@RequestHeader("Authorization") String accessToken,
                                                                     @PathVariable(value = "letterpaper_id") int letterpaper_id){

        LetterPaper letterPaperInfo = storeService.getLetterPaperDetail(letterpaper_id);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "편지지 상세 조회 성공", letterPaperInfo));
    }

    // 편지지 구매
    @PostMapping("/letterpaper/buy")
    @Operation(summary = "편지지 구매", description = "Authorization : Bearer {accessToken}, 필수 \n\n" +
                                                    "letterPaperBuyReqDto : {letterpaperId : int}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =  "CREATED", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n \"status\": 201,\n \"message\": \"편지지 구매 성공\"\n}"))),
            @ApiResponse(responseCode = "4001", description =  "NOT_EXISTS_LETTERPAPER", content = @Content(examples = @ExampleObject(value = "{\n \"httpStatus\": \"400 BAD_REQUEST\",\n \"status\": 4001,\n \"message\": \"존재하지 않는 편지지입니다.\"\n}")))}
    )
    public ResponseEntity<? extends BaseResponseBody> buyLetterPaper(@RequestHeader("Authorization") String accessToken,
                                                                     @RequestBody LetterPaperReqDto letterPaperBuyReqDto){

        UserResDto userResDto = memberService.accessTokenToMember(accessToken);
        String memberId = userResDto.getData().getMemberID();

        storeService.buyLetterPaper(memberId, letterPaperBuyReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "편지 구매 성공"));
    }


    // ########################################################


    // 캐릭터 목록 조회
    @GetMapping("/crowtit")
    @Operation(summary = "사용자가 가지고 있는 캐릭터 목록 조회 (도감용)", description = "Authorization : Bearer {accessToken}, 필수")
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
                            "           \"imagePath\": \"url\"," +
                            "           \"isowned\": \"true\"," +
                            "           \"isMain\": \"true\"" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    public ResponseEntity<? extends BaseResponseBody> getCharacterList(@RequestHeader("Authorization") String accessToken){
        UserResDto userResDto = memberService.accessTokenToMember(accessToken);
        String memberId = userResDto.getData().getMemberID();

//        List<CrowTitBook> crowTitBookList = crowTitBookRepository.findAllByMemberId(memberId);
        List<CrowTitResDto> crowTitBookList = storeService.getCrowTitList(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "캐릭터 목록 조회 성공", crowTitBookList));
    }
    @GetMapping("/crowtit/all")
    @Operation(summary = "캐릭터 목록 전체 조회 (상점용)", description = "Authorization : Bearer {accessToken}, 필수\n\n " +
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
                            "           \"imagePath\": \"url\"," +
                            "           \"isowned\": \"true\"," +
                            "           \"isMain\": \"true\"" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    public ResponseEntity<? extends BaseResponseBody> getCharacterListAll(@RequestHeader("Authorization") String accessToken){
        UserResDto userResDto = memberService.accessTokenToMember(accessToken);
        String memberId = userResDto.getData().getMemberID();

        List<CrowTitResDto> crowTitList = storeService.getCrowTitListAll(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "메인 캐릭터 조회 성공", crowTitList));
    }

    @GetMapping("/crowtit/main")
    @Operation(summary = "사용자의 메인 뱁새 반환", description = "Authorization : Bearer {accessToken}, 필수")
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
                            "           \"imagePath\": \"url\"," +
                            "           \"isowned\": \"true\"" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    public ResponseEntity<? extends BaseResponseBody> getMainCrowTit(@RequestHeader("Authorization") String accessToken){
        UserResDto userResDto = memberService.accessTokenToMember(accessToken);
        String memberId = userResDto.getData().getMemberID();

        Optional<CrowTitBook> mainCrowTit = crowTitBookRepository.findByMemberIdAndIsMain(memberId, true);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "메인 캐릭터 조회 성공", mainCrowTit));
    }

    // 캐릭터 상세 조회
    @GetMapping("/crowtit/{crowtit_id}")
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
                            "           \"imagePath\": \"url\"" +
                            "       }" +
                            "   ]" +
                            "}"))),
            @ApiResponse(responseCode = "4001", description =  "NOT_EXISTS_CROWTIT", content = @Content(examples = @ExampleObject(value = "{\n \"httpStatus\": \"400 BAD_REQUEST\",\n \"status\": 4001,\n \"message\": \"존재하지 않는 뱁새입니다.\"\n}"))),
})
    public ResponseEntity<? extends BaseResponseBody> getCharacter(@RequestHeader("Authorization") String accessToken,
                                                                   @PathVariable("crowtit_id") int crowTitId){

        CrowTit crowTitInfo = storeService.getCrowTitInfo(crowTitId);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "캐릭터 상세 조회 성공", crowTitInfo));
    }

    // 캐릭터 구매
    @PostMapping("/crowtit/buy")
    @Operation(summary = "캐릭터 구매", description = "Authorization : Bearer {accessToken}, 필수\\n\\n " +
                                                    "crowTitReqDto : { crowtit_id : int }")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =  "CREATED", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n \"status\": 201,\n \"message\": \"캐릭터 구매 성공\"\n}"))),
            @ApiResponse(responseCode = "4001", description =  "NOT_EXISTS_CROWTIT", content = @Content(examples = @ExampleObject(value = "{\n \"httpStatus\": \"400 BAD_REQUEST\",\n \"status\": 4001,\n \"message\": \"존재하지 않는 뱁새입니다.\"\n}")))}
    )
    public ResponseEntity<? extends BaseResponseBody> buyCharacter(@RequestHeader("Authorization") String accessToken,
                                                                   @RequestBody CrowTitReqDto crowTitBuyReqDto){

        UserResDto userResDto = memberService.accessTokenToMember(accessToken);
        String memberId = userResDto.getData().getMemberID();

        storeService.buyCrowTit(memberId, crowTitBuyReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "캐릭터 구매 성공"));
    }

    @PostMapping("/crowtit/change")
    @Operation(summary = "캐릭터 변경", description = "Authorization : Bearer {accessToken}, 필수\\n\\n " +
                                                     "crowTitBuyReqDto : { character_id : int }")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =  "CREATED", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n \"status\": 201,\n \"message\": \"캐릭터 구매 성공\"\n}"))),
            @ApiResponse(responseCode = "4001", description =  "NOT_EXISTS_CROWTIT", content = @Content(examples = @ExampleObject(value = "{\n \"httpStatus\": \"400 BAD_REQUEST\",\n \"status\": 4001,\n \"message\": \"존재하지 않는 뱁새입니다.\"\n}")))}
    )
    public ResponseEntity<? extends BaseResponseBody> changeMainCharacter(@RequestHeader(value = "Authorization") String accessToken,
                                                                      @RequestBody CrowTitReqDto crowTitChangeReqDto){
        UserResDto userResDto = memberService.accessTokenToMember(accessToken);
        String memberId = userResDto.getData().getMemberID();

        storeService.changeMainCrowTit(memberId, crowTitChangeReqDto);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "캐릭터 변경 성공"));

    }
    // ##############################################################################

//    @PostMapping("/upload")
//    @Operation(summary = "캐릭터 및 편지지 S3 업로드용")
//    public ResponseEntity<? extends BaseResponseBody> upload(@RequestPart(value = "name") String name,
//                                                                           @RequestPart(value = "price") Long price,
//                                                                           @RequestPart(value = "description") String description,
//                                                                           @RequestPart List<MultipartFile> files) {
////        LetterPaperReqDto letterPaperReqDto = LetterPaperReqDto.builder()
////                .name(name)
////                .price(price)
////                .description(description)
////                .build();
//
//        CrowTitReqDto crowTitReqDto = CrowTitReqDto.builder()
//                .name(name)
//                .price(price)
//                .description(description)
//                .build();
//
//        storeService.upload(crowTitReqDto, files);
//
////        storeService.upload(crowTitReqDto, files);
//        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "s3 이미지 업로드 성공"));
//    }

}
