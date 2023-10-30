package com.zootopia.userservice.service;

import com.zootopia.userservice.dto.UserInfoDTO;

public interface UserService {

    UserInfoDTO loadUserInfo(String memberID);
}
