package com.zootopia.userservice.service;

import com.zootopia.userservice.domain.User;
import com.zootopia.userservice.dto.MypageDTO;
import com.zootopia.userservice.dto.UserInfoDTO;
import com.zootopia.userservice.dto.UserRegisterDTO;
import com.zootopia.userservice.jwt.JwtProvider;
import com.zootopia.userservice.kakao.KakaoUserInfo;
import com.zootopia.userservice.mapper.UserMapper;
import com.zootopia.userservice.repository.RedisRepository;
import com.zootopia.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
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

        String memberID = user.getMemberID();
        userRepository.save(user);

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
    public String reissueAccessToken(String refreshToken) {

        String res = "";
        if (refreshToken == null) {
            return res;
        }

        String memberIDFromToken = jwtProvider.getMemberIDFromToken(refreshToken);
        if (memberIDFromToken.isEmpty()) {
            res = jwtProvider.createAccessToken(memberIDFromToken);
        }

        return res;
    }
}
