package com.zootopia.userservice.service;

import com.zootopia.userservice.common.BaseResponse;
import com.zootopia.userservice.dto.FriendInfoDTO;
import com.zootopia.userservice.mapper.FriendMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendMapper friendMapper;

    @Override
    public List<FriendInfoDTO> getFriendInfoList(String memberID) {
        return friendMapper.getFriendInfoList(memberID);
    }

    @Override
    public BaseResponse addFriend(String memberID, String opponentID) {

        BaseResponse baseResponse = new BaseResponse(200, "", null);

        if (!memberID.equals(opponentID)) {
            int res = friendMapper.addFriend(memberID, opponentID);

            if (res == 1) {
                baseResponse.setMessage("친구 추가가 완료 되었습니다.");
            } else {
                baseResponse.setMessage("이미 친구 추가가 되어있습니다.");
            }
        } else {
            baseResponse.setMessage("자기 자신에게 친구 추가를 할 수 없습니다.");
        }

        return baseResponse;
    }
}
