package com.zootopia.userservice.controller;

import com.zootopia.userservice.common.BaseResponse;
import com.zootopia.userservice.dto.UserPointTXDTO;
import com.zootopia.userservice.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    private String memberID;

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    @GetMapping("/point")
    public ResponseEntity<BaseResponse> getUserPointTransaction() {

        List<UserPointTXDTO> userPointTXDTOS = pointService.loadUserPointTransaction(memberID);
        BaseResponse baseResponse = new BaseResponse(200, "유저 포인트 사용 내역 조회", userPointTXDTOS);

        return ResponseEntity.status(200).body(baseResponse);
    }
}
