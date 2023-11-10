package com.zootopia.userservice.api;

import com.zootopia.userservice.common.BaseResponse;
import com.zootopia.userservice.dto.FriendShipDTO;
import com.zootopia.userservice.dto.UserInfoDTO;
import com.zootopia.userservice.exception.FriendException;
import com.zootopia.userservice.jwt.JwtProvider;
import com.zootopia.userservice.service.APIService;
import com.zootopia.userservice.service.PointService;
import com.zootopia.userservice.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


@Tag(name = "회원 관련 정보 조회 API",
        description = "서버간 통신에서만 이용 가능합니다. <b>Android에서 호출하면 4xx Error 발생합니다.</b>"
)
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class APIController {

    private final APIService apiService;
    private final JwtProvider jwtProvider;
    private final PointService pointService;

    @Operation(summary = "JWT로 멤버 정보 반환")
    @ApiResponse(
            responseCode = "200", description = "JWT 토큰이 필요합니다.",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "    \"status\": 200,\n" +
                            "    \"message\": \"ㅇㅇ의 회원 정보입니다.\",\n" +
                            "    \"data\": {\n" +
                            "        \"memberID\": \"\",\n" +
                            "        \"fcmToken\": \"\",\n" +
                            "        \"nickname\": \"\",\n" +
                            "        \"profileImagePath\": \"\",\n" +
                            "        \"point\": \"\",\n" +
                            "        \"createdDateTime\": \"\"\n" +
                            "    }\n" +
                            "}")
            )
    )
    @GetMapping("/user")
    public ResponseEntity<BaseResponse> getMemberInfoByJWT(HttpServletRequest request) {

        String accessToken = JwtUtil.getTokenFromHeader(request);

        UserInfoDTO userInfo = apiService.loadUserInfo(accessToken);
        BaseResponse baseResponse = new BaseResponse(
                200,
                userInfo.getNickname().concat("의 회원 정보입니다."),
                userInfo);

        return ResponseEntity.status(200).body(baseResponse);
    }

    @Operation(summary = "멤버 ID로 FCM 토큰, 닉네임 반환")
    @ApiResponse(
            responseCode = "200", description = "JWT 토큰이 필요없습니다.",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "    \"status\": 200,\n" +
                            "    \"message\": \"FCM 토큰, 닉네임 반환되었습니다.\",\n" +
                            "    \"data\": {\n" +
                            "        \"nickname\": \"\",\n" +
                            "        \"fcmToken\": \"\"\n" +
                            "    }\n" +
                            "}")
            )
    )
    @GetMapping("/user/{memberID}")
    public ResponseEntity<BaseResponse> getMemberInfoByMemberID(@PathVariable String memberID) {

        UserInfoDTO userInfo = apiService.loadUserInfoByMemberID(memberID);
        BaseResponse baseResponse = new BaseResponse();

        if (userInfo == null) {
            baseResponse.setStatus(400);
            baseResponse.setMessage("해당 유저가 없습니다.");
            baseResponse.setData(null);
            return ResponseEntity.status(400).body(baseResponse);
        }

        HashMap<String, String> res = new HashMap<>();
        res.put("fcmToken", userInfo.getFcmToken());
        res.put("nickname", userInfo.getNickname());

        baseResponse.setStatus(200);
        baseResponse.setMessage("FCM 토큰, 닉네임 반환되었습니다.");
        baseResponse.setData(res);

        return ResponseEntity.status(200).body(baseResponse);
    }

    @Operation(summary = "멤버 A와 멤버 B의 친구 관계 정보")
    @ApiResponse(
            responseCode = "200", description = "JWT 토큰이 필요합니다.",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "[\n" +
                            "        {\n" +
                            "            \"from\": \"ssafyA\",\n" +
                            "            \"to\": \"ssafyB\",\n" +
                            "            \"blocked\": false\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"from\": \"ssafyB\",\n" +
                            "            \"to\": \"ssafyA\",\n" +
                            "            \"blocked\": false\n" +
                            "        }\n" +
                            "    ]")
            )
    )
    @PostMapping("/friend")
    public ResponseEntity<BaseResponse> verifyFriendShip(@RequestBody FriendShipDTO friendShipDTO) {

        // Destructuring DTO
        String from = friendShipDTO.getFrom();
        String to = friendShipDTO.getTo();

        List<FriendShipDTO> res = apiService.checkRelationshipWithFriends(from, to);
        String desc = String.format("%s과 %s의 친구 관계 정보입니다.", from, to);

        BaseResponse baseResponse = new BaseResponse(
                200,
                desc,
                res);

        return ResponseEntity.status(200).body(baseResponse);
    }

    @Operation(summary = "멤버 ID로 임시 JWT 토큰 반환")
    @ApiResponse(
            responseCode = "200", description = "JWT 토큰이 필요없습니다.",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "JWT 토큰")
            )
    )
    @GetMapping("/token/{memberID}")
    public String getTmpToken(@PathVariable String memberID) {
        return jwtProvider.createAccessToken(memberID);
    }

    @PostMapping("/point/{memberID}/{point}")
    public ResponseEntity<BaseResponse>  renewUserPoint(@PathVariable String memberID, @PathVariable int point) {
        int res = pointService.reviseUserPoint(memberID, point);

        int status = 200;
        BaseResponse baseResponse;
        if (res == 1) {
            baseResponse = new BaseResponse(status, "유저 포인트가 변경되었습니다", null);
        } else {
            status = 400;
            baseResponse = new BaseResponse(status, "유저 포인트 변경을 실패했습니다.", null);
        }

        return ResponseEntity.status(status).body(baseResponse);
    }
}
