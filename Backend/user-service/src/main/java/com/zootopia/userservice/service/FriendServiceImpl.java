package com.zootopia.userservice.service;

import com.zootopia.userservice.dto.FriendInfoDTO;
import com.zootopia.userservice.mapper.FriendMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final FriendMapper friendMapper;

    @Override
    public List<FriendInfoDTO> getFriendInfoList(String memberID) {
        return friendMapper.getFriendInfoList(memberID);
    }
}
