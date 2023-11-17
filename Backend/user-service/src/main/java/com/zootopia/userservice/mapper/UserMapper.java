package com.zootopia.userservice.mapper;

import com.zootopia.userservice.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserMapper {

    MypageDTO readUserInfo(String memberID);

    List<UserSearchDTO> readLimitUserByID(UserSearchParam paramMap);

    List<UserSearchDTO> readLimitUserByIDWithFriendRelation(UserSearchParam paramMap);

    void updateUserPoint(
            @Param(value = "memberID") String memberID,
            @Param(value = "point") int point
    );

    int updateFCMToken(
            @Param(value = "memberID") String memberID,
            @Param(value = "token") String token
    );

    UserInfoDTO findByMemberID(String memberID);

    void saveUser(InsertUserDTO userDTO);
}
