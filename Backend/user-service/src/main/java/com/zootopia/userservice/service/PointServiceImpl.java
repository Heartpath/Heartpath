package com.zootopia.userservice.service;

import com.zootopia.userservice.dto.UserPointTXDTO;
import com.zootopia.userservice.mapper.PointMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointMapper pointMapper;

    @Override
    public List<UserPointTXDTO> loadUserPointTransaction(String memberID) {
        return pointMapper.readUserPointTransaction(memberID);
    }
}
