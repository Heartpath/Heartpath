package com.zootopia.userservice.mapper;

import com.zootopia.userservice.dto.FriendInfoDTO;
import com.zootopia.userservice.dto.FriendShipDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface FriendMapper {

    List<FriendShipDTO> getRelationshipWithFriends(String from, String to);

    List<FriendInfoDTO> getFriendInfoList(String memberID);

    int addFriend(@Param(value = "from") String from, @Param(value = "to") String to);

    int blockOrUnblockOffFriend(
            @Param(value = "from") String from,
            @Param(value = "to") String to,
            @Param(value = "option") int option
    );

    List<FriendInfoDTO> getBlockOffFriendList(String memberID);
}
