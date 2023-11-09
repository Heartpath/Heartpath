package com.zootopia.userservice.api;

import com.zootopia.userservice.common.BaseResponse;
import com.zootopia.userservice.dto.FriendShipDTO;
import com.zootopia.userservice.dto.UserInfoDTO;
import com.zootopia.userservice.exception.FriendException;
import com.zootopia.userservice.jwt.JwtProvider;
import com.zootopia.userservice.service.APIService;
import com.zootopia.userservice.util.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


@Tag(name = "회원 관련 정보 조회 API", description = "모든 요청은 AccessToken이 필요합니다. Header에 담아서 보내주세요. \n Authorization: ")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class APIController {

    private final APIService apiService;
    private final JwtProvider jwtProvider;

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

    @PostMapping("/friend")
    public ResponseEntity<BaseResponse> verifyFriendShip(@RequestBody FriendShipDTO friendShipDTO) {

        // Destructuring DTO
        String from = friendShipDTO.getFrom();
        String to = friendShipDTO.getTo();

        List<FriendShipDTO> res = apiService.checkRelationshipWithFriends(from, to);

        BaseResponse baseResponse = new BaseResponse(
                200,
                "회원 정보",
                res);

        return ResponseEntity.status(200).body(baseResponse);
    }

    @GetMapping("/token/{memberID}")
    public String getTmpToken(@PathVariable String memberID) {
        return jwtProvider.createAccessToken(memberID);
    }
}
