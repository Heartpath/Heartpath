package com.zootopia.userservice.mapper;

import com.zootopia.userservice.dto.MypageDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    MypageDTO readUserInfo(String memberID);
}
