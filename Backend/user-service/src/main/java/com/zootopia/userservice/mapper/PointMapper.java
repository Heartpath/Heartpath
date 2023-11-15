package com.zootopia.userservice.mapper;

import com.zootopia.userservice.dto.UserPointTXDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface PointMapper {

    List<UserPointTXDTO> readUserPointTransaction(String memberID);

    int updateUserPoint(
            @Param(value = "memberID") String memberID,
            @Param(value = "point") int point
    );
}
