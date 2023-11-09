package com.zootopia.userservice.controller;

import com.zootopia.userservice.common.BaseResponse;
import com.zootopia.userservice.dto.MypageDTO;
import com.zootopia.userservice.dto.UserInfoDTO;
import com.zootopia.userservice.dto.UserLoginDTO;
import com.zootopia.userservice.dto.UserRegisterDTO;
import com.zootopia.userservice.jwt.JwtProvider;
import com.zootopia.userservice.kakao.KakaoOAuthService;
import com.zootopia.userservice.service.UserService;
import com.zootopia.userservice.util.JwtUtil;
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

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final KakaoOAuthService kakaoOAuthService;

    @GetMapping("/health_check")
    public String checkServer(HttpServletRequest request) {

        String remoteAddr = request.getRemoteAddr();
        log.info("Get Request From '{}'", remoteAddr);

        return String.format("200 OK to %s", remoteAddr);
    }

    @Operation(summary = "회원 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "회원 가입을 해야하는 경우", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "        \"status\": 202,\n" +
                            "        \"message\": \"false\",\n" +
                            "        \"data\": {}\n" +
                            "}"))),
            @ApiResponse(responseCode = "200", description = "회원 가입이 되어있을 경우", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "        \"status\": 200,\n" +
                            "        \"message\": \"true\",\n" +
                            "        \"data\": {\n" +
                            "                \"AccessToken\": \"AccessTokenValue\",\n" +
                            "                \"RefreshToken\": \"RefreshTokenValue\"\n" +
                            "        }\n" +
                            "}")))
    })
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> loginUser(@RequestBody UserLoginDTO userLoginDTO) {

        // Destructuring DTO
        String userKakaoToken = userLoginDTO.getKakaoToken();
        String userFCMToken = userLoginDTO.getFcmToken();

        // 카카오 로그인 진행
        BaseResponse baseResponse = kakaoOAuthService.doKakaoLogin(userKakaoToken, userFCMToken);

        // 회원 가입 여부에 따라 status 변경
        int status = 200;
        if (baseResponse.getMessage().equals("false")) {
            status = 202;
            baseResponse.setStatus(status);
        }

        return ResponseEntity.status(status).body(baseResponse);
    }

    @Operation(summary = "아이디 중복 검사", description = "쿼리 파라미터로 중복 검사할 아이디 넘겨주세요.")
    @ApiResponses(value = {
            @ApiResponse(description = "아이디가 중복되지 않을 경우", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "        \"status\": 200,\n" +
                            "        \"message\": \"사용할 수 없는 아이디입니다.\",\n" +
                            "        \"data\": true\n" +
                            "}\n"))),
            @ApiResponse(responseCode = "200", description = "아이디가 중복됐을 경우", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "        \"status\": 200,\n" +
                            "        \"message\": \"사용할 수 있는 아이디입니다.\",\n" +
                            "        \"data\": false\n" +
                            "}\n"))),
    })
    @GetMapping("/check")
    public ResponseEntity<BaseResponse> checkDuplicatedUserID(@RequestParam(name = "id") String memberID) {

        boolean res = userService.checkIfDuplicatedUserID(memberID);

        BaseResponse baseResponse = new BaseResponse(200, "사용할 수 없는 아이디입니다.", res);
        if (!res) {
            baseResponse.setMessage("사용할 수 있는 아이디입니다.");
        }

        return ResponseEntity.status(200).body(baseResponse);
    }

    @Operation(summary = "회원 가입", description = "로그인 때 전달했던 동일한 KakaoToken을 전달해주세요.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "        \"status\": 200,\n" +
                            "        \"message\": \"회원가입이 완료되었습니다.\",\n" +
                            "        \"data\": []\n" +
                            "}\n"))),
    })
    @PostMapping("/register")
    public ResponseEntity<BaseResponse> registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        HashMap<String, String> tokens = userService.registerUser(userRegisterDTO);

        BaseResponse baseResponse = new BaseResponse(200, "회원가입이 완료되었습니다.", tokens);
        return ResponseEntity.status(200).body(baseResponse);
    }

    @GetMapping("/token")
    public ResponseEntity<BaseResponse> reissueAccessToken(@RequestParam(name = "refreshToken") String refreshToken) {

        int status = 200;
        String reissuedAccessToken = userService.reissueAccessToken(refreshToken);

        BaseResponse baseResponse = new BaseResponse(status, "AccessToken이 재발급되었습니다.", reissuedAccessToken);
        if (reissuedAccessToken.isEmpty()) {

            status = 400;

            baseResponse.setMessage("오류가 발생했습니다.");
            baseResponse.setStatus(status);
        }

        return ResponseEntity.status(status).body(baseResponse);
    }

    @GetMapping("/mypage")
    public ResponseEntity<BaseResponse> getUserInfo(HttpServletRequest request) {

        String accessToken = JwtUtil.getTokenFromHeader(request);
        String memberID = jwtProvider.getMemberIDFromToken(accessToken);

        MypageDTO mypageDTO = userService.loadUserInfo(memberID);

        return ResponseEntity.status(200).body(new BaseResponse(HttpStatus.OK.value(), "유저 불러오기", mypageDTO));
    }
}
