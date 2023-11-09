package com.zootopia.userservice.mapper;

import com.zootopia.userservice.dto.FriendInfoDTO;
import com.zootopia.userservice.dto.FriendShipDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface FriendMapper {

    List<FriendShipDTO> getRelationshipWithFriends(String from, String to);

    List<FriendInfoDTO> getFriendInfoList(String memberID);
}
