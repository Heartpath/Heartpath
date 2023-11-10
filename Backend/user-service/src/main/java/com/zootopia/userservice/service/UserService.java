package com.zootopia.userservice.service;

import com.zootopia.userservice.dto.MypageDTO;
import com.zootopia.userservice.dto.UserInfoDTO;
import com.zootopia.userservice.dto.UserRegisterDTO;
import com.zootopia.userservice.dto.UserSearchDTO;

import java.util.HashMap;
import java.util.List;

public interface UserService {

    MypageDTO loadUserInfo(String memberID);

    boolean checkIfDuplicatedUserID(String memberID);

    HashMap<String, String> registerUser(UserRegisterDTO userRegisterDTO);

    String reissueAccessToken(String refreshToken);

    List<UserSearchDTO> searchUserByID(String findUserID, int limit);
}
