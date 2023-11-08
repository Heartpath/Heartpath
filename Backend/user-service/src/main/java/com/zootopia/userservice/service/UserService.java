package com.zootopia.userservice.service;

import com.zootopia.userservice.dto.MypageDTO;
import com.zootopia.userservice.dto.UserInfoDTO;
import com.zootopia.userservice.dto.UserRegisterDTO;

import java.util.HashMap;

public interface UserService {

    MypageDTO loadUserInfo(String memberID);

    boolean checkIfDuplicatedUserID(String memberID);

    HashMap<String, String> registerUser(UserRegisterDTO userRegisterDTO);

    String reissueAccessToken(String refreshToken);
}
