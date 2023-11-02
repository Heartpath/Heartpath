package com.zootopia.userservice.controller;

import com.zootopia.userservice.common.BaseResponse;
import com.zootopia.userservice.dto.UserInfoDTO;
import com.zootopia.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/health_check")
    public String checkServer(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();

        return String.format("200 OK to %s", remoteAddr);
    }

    @GetMapping("/mypage")
    public ResponseEntity<BaseResponse> getUserInfo() {

        UserInfoDTO userInfoDTO = userService.loadUserInfo("MEMBER_ID");

        return ResponseEntity.status(200).body(new BaseResponse(HttpStatus.OK, "유저 불러오기", userInfoDTO));
    }
}
