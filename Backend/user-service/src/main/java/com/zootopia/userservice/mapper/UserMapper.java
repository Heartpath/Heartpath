package com.zootopia.userservice.mapper;

import com.zootopia.userservice.dto.MypageDTO;
import com.zootopia.userservice.dto.UserSearchDTO;
import com.zootopia.userservice.dto.UserSearchParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper {

    MypageDTO readUserInfo(String memberID);

    List<UserSearchDTO> readLimitUserByID(UserSearchParam paramMap);

    List<UserSearchDTO> readLimitUserByIDWithFriendRelation(UserSearchParam paramMap);
}
