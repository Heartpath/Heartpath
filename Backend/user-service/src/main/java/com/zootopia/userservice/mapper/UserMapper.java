package com.zootopia.userservice.mapper;

import com.zootopia.userservice.dto.MypageDTO;
import com.zootopia.userservice.dto.QueryParamMap;
import com.zootopia.userservice.dto.UserSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper {

    MypageDTO readUserInfo(String memberID);

    List<UserSearchDTO> readLimitUserByID(QueryParamMap paramMap);
}
