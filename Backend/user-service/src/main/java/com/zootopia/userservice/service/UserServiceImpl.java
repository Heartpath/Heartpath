package com.zootopia.userservice.service;

import com.zootopia.userservice.dto.UserInfoDTO;
import com.zootopia.userservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserInfoDTO loadUserInfo(String memberID) {
        UserInfoDTO userInfoDTO = userMapper.readUserInfo(memberID);
        return userInfoDTO;
    }
}
