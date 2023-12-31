package com.zootopia.userservice.service;


import com.zootopia.userservice.dto.FriendShipDTO;
import com.zootopia.userservice.dto.UserInfoDTO;

import java.util.List;


public interface APIService {

    UserInfoDTO loadUserInfo(String token);

    UserInfoDTO loadUserInfoByMemberID(String memberID);

    List<FriendShipDTO> checkRelationshipWithFriends(String from, String to);
}
