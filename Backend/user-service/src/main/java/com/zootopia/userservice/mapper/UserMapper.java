package com.zootopia.userservice.mapper;

import com.zootopia.userservice.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    UserInfoDTO readUserInfo(String memberID);
}
