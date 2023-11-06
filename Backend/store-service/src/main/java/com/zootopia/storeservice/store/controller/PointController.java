package com.zootopia.storeservice.store.controller;


import com.zootopia.storeservice.common.dto.BaseResponseBody;
import com.zootopia.storeservice.store.dto.request.PointTransReqDto;
import com.zootopia.storeservice.store.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class PointController {

    @Autowired
    private WebClient webClient;

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
//        String memberId = webClient
//            .post()
//            .header("Authorization", accessToken)
//            .retrieve()
//            .bodyToMono(Object.class.getId());
//
//        List<Point> pointUsage = pointService.getPointUsage(memberId);
//        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "사용자 포인트 내역 조회 성공", pointUsage));
        return null;
    }

    @PostMapping("/point")
    @Operation(summary = "포인트 적립", description = "Authorization : Bearer {accessToken}, 필수\n\n " +
    "pointTransReqDto : { point : int }")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description =  "CREATED", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n \"status\": 201,\n \"message\": \"포인트 적립 성공\"\n}")))}
    )
    public ResponseEntity<? extends BaseResponseBody> transactionPoint(@RequestHeader("Authorization") String accessToken,
                                                                       @RequestBody PointTransReqDto pointTransReqDto){
//        String memberId = webClient
//            .post()
//            .header("Authorization", accessToken)
//            .retrieve()
//            .bodyToMono(Object.class.getId());
//
//        pointService.transactionPoint(memberId, pointTransReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "포인트 적립 성공"));
    }
}
