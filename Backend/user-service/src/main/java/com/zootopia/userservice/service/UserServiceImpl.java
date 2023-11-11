package com.zootopia.userservice.service;

import com.zootopia.userservice.domain.User;
import com.zootopia.userservice.dto.*;
import com.zootopia.userservice.exception.JwtException;
import com.zootopia.userservice.jwt.JwtProvider;
import com.zootopia.userservice.kakao.KakaoUserInfo;
import com.zootopia.userservice.mapper.UserMapper;
import com.zootopia.userservice.repository.RedisRepository;
import com.zootopia.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtProvider jwtProvider;

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    private final RedisRepository redisRepository;

    @Override
    public boolean checkIfDuplicatedUserID(String memberID) {
        return userRepository.existsByMemberID(memberID);
    }

    @Override
    public HashMap<String, String> registerUser(UserRegisterDTO userRegisterDTO) {

        String kakaoToken = userRegisterDTO.getKakaoToken();
        Optional<KakaoUserInfo> oKakaoUserInfo = redisRepository.getData(kakaoToken, KakaoUserInfo.class);
        if (oKakaoUserInfo.isEmpty()) {
            throw new NullPointerException("카카오 로그인을 다시 진행해주세요.");
        }

        KakaoUserInfo kakaoUserInfo = oKakaoUserInfo.get();

        User user = User.builder()
                .nickname(kakaoUserInfo.getNickname())
                .profileImagePath(kakaoUserInfo.getProfileImageURL())
                .kakaoID(kakaoUserInfo.getKakaoId())
                .point(0)
                .createdDate(LocalDateTime.now())
                .fcmToken(userRegisterDTO.getFcmToken())
                .memberID(userRegisterDTO.getMemberID())
                .build();
        log.info("save user: {}", user);

        // 유저 정보 저장
        String memberID = user.getMemberID();
        userRepository.save(user);

        // 기본 캐릭터 정보 저장
        final String URL = "http://3.37.181.29/api/default/".concat(memberID);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(
                URL,
                HttpMethod.POST,
                null,
                String.class
        );

        // 토큰 발행
        String accessToken = jwtProvider.createAccessToken(memberID);
        String refreshToken = jwtProvider.createRefreshToken(memberID);

        HashMap<String, String> res = new HashMap<>();
        res.put("AccessToken", accessToken);
        res.put("RefreshToken", refreshToken);

        return res;
    }

    @Override
    public MypageDTO loadUserInfo(String memberID) {
        MypageDTO userInfoDTO = userMapper.readUserInfo(memberID);
        return userInfoDTO;
    }

    @Override
    public String reissueAccessToken(String refreshToken) throws JwtException {

        String res = "";
        if (refreshToken == null) {
            return res;
        }

        log.info("try to reissue AccessToken");
        log.info("With Given Token: {}", refreshToken);
        jwtProvider.validateToken(refreshToken);

        String memberIDFromToken = jwtProvider.getMemberIDFromToken(refreshToken);
        if (!memberIDFromToken.isEmpty()) {
            res = jwtProvider.createAccessToken(memberIDFromToken);
        }

        return res;
    }

    @Override
    public List<UserSearchDTO> searchUser(String query, int limit, String memberID, boolean checkFriends) {

        List<UserSearchDTO> res;

        if (checkFriends) {
            res = searchUserByIDWithFriendRelation(memberID, query, limit);
        } else {
            res = searchUserByID(query, limit);
        }

        return res;
    }

    private List<UserSearchDTO> searchUserByID(String query, int limit) {
        return userMapper.readLimitUserByID(new UserSearchParam(query, limit));
    }

    private List<UserSearchDTO> searchUserByIDWithFriendRelation(String memberID, String query, int limit) {
        return userMapper.readLimitUserByIDWithFriendRelation(new UserSearchParam(memberID, query, limit));
    }
}
