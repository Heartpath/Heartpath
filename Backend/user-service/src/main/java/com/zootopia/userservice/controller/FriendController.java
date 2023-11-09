package com.zootopia.userservice.controller;

import com.zootopia.userservice.common.BaseResponse;
import com.zootopia.userservice.dto.FriendInfoDTO;
import com.zootopia.userservice.service.FriendService;
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
public class FriendController {

    private final FriendService friendService;

    private String memberID;

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    @GetMapping("/friend")
    public ResponseEntity<BaseResponse> getFriendList() {

        List<FriendInfoDTO> friendInfoList = friendService.getFriendInfoList(memberID);
        for (FriendInfoDTO friendInfoDTO : friendInfoList) {
            System.out.println("friendInfoDTO = " + friendInfoDTO);
        }

        BaseResponse baseResponse = new BaseResponse(200, "친구 목록", friendInfoList);
        return ResponseEntity.status(200).body(baseResponse);
    }
}
