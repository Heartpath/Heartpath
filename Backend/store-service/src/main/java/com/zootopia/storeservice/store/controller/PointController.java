package com.zootopia.storeservice.store.controller;


import com.zootopia.storeservice.common.dto.BaseResponseBody;
import com.zootopia.storeservice.store.dto.request.PointTransReqDto;
import com.zootopia.storeservice.store.service.PointService;
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
    public ResponseEntity<? extends BaseResponseBody> getPointUsage(@RequestHeader("Authorization") String accessToken){
//        String memberId = webClient
//            .post()
//            .header("Authorization", accessToken)
//            .retrieve()
//            .bodyToMono(Object.class.getId());
//
//        List<Point> pointUsage = pointService.getPointUsage(memberId);
//        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "포인트 조회 성공", pointUsage));
        return null;
    }

    @PostMapping("/point")
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
