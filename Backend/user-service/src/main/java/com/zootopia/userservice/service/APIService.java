package com.zootopia.userservice.service;


import com.zootopia.userservice.dto.UserInfoDTO;

public interface APIService {

    UserInfoDTO loadUserInfo(String token);
}
