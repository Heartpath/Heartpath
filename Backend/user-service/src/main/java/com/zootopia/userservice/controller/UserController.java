package com.zootopia.userservice.controller;

import com.zootopia.userservice.common.BaseResponse;
import com.zootopia.userservice.dto.UserInfoDTO;
import com.zootopia.userservice.dto.UserLoginDTO;
import com.zootopia.userservice.dto.UserRegisterDTO;
import com.zootopia.userservice.kakao.KakaoOAuthService;
import com.zootopia.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoOAuthService kakaoOAuthService;

    @GetMapping("/health_check")
    public String checkServer() {
        return "200 OK";
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> loginUser(@RequestBody UserLoginDTO userLoginDTO) {

        // Destructuring DTO
        String userKakaoToken = userLoginDTO.getKakaoToken();
        String userFCMToken = userLoginDTO.getFcmToken();

        BaseResponse baseResponse = kakaoOAuthService.doKakaoLogin(userKakaoToken, userFCMToken);
        return ResponseEntity.status(200).body(baseResponse);
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        userService.registerUser(userRegisterDTO);
    }

    @GetMapping("/mypage")
    public ResponseEntity<BaseResponse> getUserInfo() {

        UserInfoDTO userInfoDTO = userService.loadUserInfo("MEMBER_ID");

        return ResponseEntity.status(200).body(new BaseResponse(HttpStatus.OK.value(), "유저 불러오기", userInfoDTO));
    }
}
