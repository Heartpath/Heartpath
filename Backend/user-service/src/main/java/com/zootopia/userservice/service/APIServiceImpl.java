package com.zootopia.userservice.service;

import com.zootopia.userservice.domain.User;
import com.zootopia.userservice.dto.UserInfoDTO;
import com.zootopia.userservice.jwt.JwtProvider;
import com.zootopia.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class APIServiceImpl implements APIService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public UserInfoDTO loadUserInfo(String token) {

        String memberIDFromToken = jwtProvider.getMemberIDFromToken(token);
        if (memberIDFromToken.isEmpty()) {
            return null;
        }

        Optional<User> findUser = userRepository.findByMemberID(memberIDFromToken);
        if (findUser.isEmpty()) {
            return null;
        }

        User user = findUser.get();

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.ofEntity(user);

        return userInfoDTO;
    }
}
