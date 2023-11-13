package com.zootopia.userservice.controller;

import com.zootopia.userservice.common.BaseResponse;
import com.zootopia.userservice.dto.MypageDTO;
import com.zootopia.userservice.dto.UserLoginDTO;
import com.zootopia.userservice.dto.UserRegisterDTO;
import com.zootopia.userservice.dto.UserSearchDTO;
import com.zootopia.userservice.exception.JwtException;
import com.zootopia.userservice.jwt.JwtProvider;
import com.zootopia.userservice.kakao.KakaoOAuthService;
import com.zootopia.userservice.service.UserService;
import com.zootopia.userservice.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


@Tag(name = "유저 관련 정보 조회 API",
        description = "<b>JWT가 필요한 Method</b> \n" +
                "1. [GET] /user/mypage (마이페이지)\n" +
                "2. [PUT] /user/mypage (유저 정보 수정)"
)
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final KakaoOAuthService kakaoOAuthService;

    @Operation(summary = "User-Service 통신 테스트")
    @GetMapping("/health_check")
    public String checkServer(HttpServletRequest request) {
        return String.format("200 OK to %s", request.getRemoteAddr());
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

    @Operation(summary = "AccessToken 재발급", description = "RefreshToken이 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "        \"status\": 200,\n" +
                            "        \"message\": \"AccessToken이 재발급되었습니다..\",\n" +
                            "        \"data\": \"reissuedAccessToken\"\n" +
                            "}\n"))),
    })
    @GetMapping("/token")
    public ResponseEntity<BaseResponse> reissueAccessToken(@RequestParam(name = "refreshToken") String refreshToken) throws JwtException {

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

    @Operation(summary = "마이페이지 정보 API", description = "JWT 토큰이 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "    \"status\": 200,\n" +
                            "    \"message\": \"메인 페이지의 유저 정보\",\n" +
                            "    \"data\": {\n" +
                            "        \"memberID\": \"\",\n" +
                            "        \"nickname\": \"\",\n" +
                            "        \"profileImagePath\": \"\",\n" +
                            "        \"characterImagePath\": \"\",\n" +
                            "        \"point\": \"\"\n" +
                            "    }\n" +
                            "}"))),
    })
    @GetMapping("/mypage")
    public ResponseEntity<BaseResponse> getUserInfo(HttpServletRequest request) {

        String accessToken = JwtUtil.getTokenFromHeader(request);
        String memberID = jwtProvider.getMemberIDFromToken(accessToken);

        MypageDTO mypageDTO = userService.loadUserInfo(memberID);

        return ResponseEntity.status(200).body(new BaseResponse(HttpStatus.OK.value(), "메인 페이지의 유저 정보", mypageDTO));
    }

    @Operation(
            summary = "유저 ID로 유저 검색",
            description = "JWT 토큰이 필요없습니다. \n 검색한 유저 ID와 유사한 유저 정보를 <b>'limit 개수'만큼 반환</b>합니다." +
                    "{\n" +
                    "  \"id\": \"검색할 유저 ID\",\n" +
                    "  \"limit\": \"반환할 유저 정보 개수\"\n" +
                    "}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "  \"status\": 200,\n" +
                            "  \"message\": \"유저 검색 결과입니다.\",\n" +
                            "  \"data\": [\n" +
                            "    {\n" +
                            "      \"memberID\": \"\",\n" +
                            "      \"nickname\": \"\",\n" +
                            "      \"profileImagePath\": \"\",\n" +
                            "      \"isFriend\": \"\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"memberID\": \"\",\n" +
                            "      \"nickname\": \"\",\n" +
                            "      \"profileImagePath\": \"\",\n" +
                            "      \"isFriend\": \"\"\n" +
                            "    }\n" +
                            "  ]\n" +
                            "}"))),
    })
    @GetMapping("/search")
    public ResponseEntity<BaseResponse> searchUserByID(
            @RequestParam(name = "id") String query,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "checkFriends") boolean checkFriends,
            HttpServletRequest request
    ) {

        String accessToken = JwtUtil.getTokenFromHeader(request);
        String memberID = jwtProvider.getMemberIDFromToken(accessToken);

        List<UserSearchDTO> res = userService.searchUser(query, limit, memberID, checkFriends);

        BaseResponse baseResponse = new BaseResponse(200, "유저 검색 결과입니다.", res);

        return ResponseEntity.status(200).body(baseResponse);
    }

    @Operation(summary = "유저 정보(닉네임, 프로필 사진) 수정 API")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse> renewUserInfo(
            @RequestPart(value = "nickname", required = false) String nickname,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
            HttpServletRequest request
    ) {

        String accessToken = JwtUtil.getTokenFromHeader(request);
        String memberID = jwtProvider.getMemberIDFromToken(accessToken);

        HashMap<String, String> res = userService.reviseUserInfo(profileImage, nickname, memberID);

        BaseResponse baseResponse = new BaseResponse(200, "변경된 유저 정보입니다.", res);
        log.info("'{}' 유저 정보 수정: {}", memberID, res);

        return ResponseEntity.status(200).body(baseResponse);
    }
}
