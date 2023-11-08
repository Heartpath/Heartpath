package com.zootopia.userservice.service;

import com.zootopia.userservice.dto.UserInfoDTO;
import com.zootopia.userservice.dto.UserRegisterDTO;

public interface UserService {

    UserInfoDTO loadUserInfo(String memberID);

    boolean checkIfDuplicatedUserID(String memberID);

    void registerUser(UserRegisterDTO userRegisterDTO);

    String reissueAccessToken(String refreshToken);
}
