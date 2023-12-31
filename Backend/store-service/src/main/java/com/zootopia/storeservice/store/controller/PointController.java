package com.zootopia.storeservice.store.controller;


import com.zootopia.storeservice.common.dto.BaseResponseBody;
import com.zootopia.storeservice.store.dto.request.PointTransReqDto;
import com.zootopia.storeservice.store.dto.response.UserResDto;
import com.zootopia.storeservice.store.entity.Point;
import com.zootopia.storeservice.store.service.MemberService;
import com.zootopia.storeservice.store.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store")
public class PointController {

    private final MemberService memberService;
    private final PointService pointService;

    @GetMapping("/point")
    @Operation(summary = "포인트 조회", description = "Authorization : Bearer {accessToken}, 필수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "OK", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{" +
                            " \"status\": 200," +
                            " \"message\": \"사용자 포인트 내역 조회 성공\"," +
                            " \"data\": [" +
                            "       {" +
                            "           \"outline\": \"스웩뱁새\"," +
                            "           \"price\": -200," +
                            "           \"balance\": 1000," +
                            "           \"date\": \"2023-10-03T05:54:10.541234\"" +
                            "       }" +
                            "   ]" +
                            "}")))
    })
    public ResponseEntity<? extends BaseResponseBody> getPointUsage(@RequestHeader("Authorization") String accessToken){

        String memberId = memberService.accessTokenToMember(accessToken).getData().getMemberID();
        log.warn(memberId);
        List<Point> pointUsage = pointService.getPointUsage(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "사용자 포인트 내역 조회 성공", pointUsage));
    }

    @PostMapping("/point")
    @Operation(summary = "포인트 적립", description = "Authorization : Bearer {accessToken}, 필수\n\n " +
    "pointTransReqDto : { point : int }\n\n"+
    "뱁새를 잡으면 포인트가 적립됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =  "CREATED", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n \"status\": 201,\n \"message\": \"포인트 적립 성공\"\n}")))}
    )
    public ResponseEntity<? extends BaseResponseBody> transactionPoint(@RequestHeader("Authorization") String accessToken,
                                                                       @RequestBody PointTransReqDto pointTransReqDto){
        UserResDto userResDto = memberService.accessTokenToMember(accessToken);
        String memberId = userResDto.getData().getMemberID();

        pointService.transactionPoint(memberId, pointTransReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "포인트 적립 성공"));
    }
}
