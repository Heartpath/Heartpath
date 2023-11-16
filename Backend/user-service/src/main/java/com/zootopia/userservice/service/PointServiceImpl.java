package com.zootopia.userservice.service;

import com.zootopia.userservice.dto.UserPointTXDTO;
import com.zootopia.userservice.mapper.PointMapper;
import com.zootopia.userservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final UserMapper userMapper;
    private final PointMapper pointMapper;

    @Override
    public List<UserPointTXDTO> loadUserPointTransaction(String memberID) {
        return pointMapper.readUserPointTransaction(memberID);
    }

    @Override
    public int reviseUserPoint(String memberID, int point) {
        log.info("change member point");
        int i = userMapper.updateUserPoint(memberID, point);
        log.info("changePoint {} {} : {}", i, memberID, point);
        return i;
    }
}
