package com.zootopia.userservice.mapper;

import com.zootopia.userservice.dto.UserPointTXDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PointMapper {

    List<UserPointTXDTO> readUserPointTransaction(String memberID);
}
