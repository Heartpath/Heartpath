package com.zootopia.userservice.service;

import com.zootopia.userservice.common.BaseResponse;
import com.zootopia.userservice.dto.FriendInfoDTO;

import java.util.List;

public interface FriendService {

    List<FriendInfoDTO> getFriendInfoList(String memberID);

    BaseResponse addFriend(String memberID, String opponentID);

    BaseResponse blockOffFriend(String memberID, String opponentID);
}
