package com.zootopia.userservice.service;

import com.zootopia.userservice.common.BaseResponse;
import com.zootopia.userservice.dto.FriendInfoDTO;
import com.zootopia.userservice.exception.FriendException;
import com.zootopia.userservice.mapper.FriendMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zootopia.userservice.exception.FriendErrorCode.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendMapper friendMapper;

    @Override
    public List<FriendInfoDTO> getFriendInfoList(String memberID) {
        return friendMapper.getBlockOffOrFriendInfoList(memberID, 0);
    }

    @Override
    public BaseResponse addFriend(String memberID, String opponentID) {

        BaseResponse baseResponse = new BaseResponse(200, "친구 추가가 완료되었습니다.", null);

        // 자기 자신에게 친구 추가할 경우
        if (memberID.equals(opponentID)) {
            throw new FriendException(ADD_FRIEND_TO_MYSELF);
        }

        // 알 수 없는 유저에게 친구 추가할 경우
        int res;
        try {
            res = friendMapper.addFriend(memberID, opponentID);
        } catch (DataIntegrityViolationException e) {
            throw new FriendException(NON_EXISTENT_USER);
        }

        // 이미 친구 추가가 되었을 경우
        if (res == 0) {
            throw new FriendException(ALREADY_ADDED_FRIEND);
        }

        return baseResponse;
    }

    @Override
    public BaseResponse blockOffFriend(String memberID, String opponentID) {

        // 자기 자신을 차단할 경우
        if (memberID.equals(opponentID)) {
            throw new FriendException(BLOCK_OFF_MYSELF);
        }

        // 존재하지 않는 유저를 차단할 경우
        try {
            friendMapper.blockOrUnblockOffFriend(memberID, opponentID, 1);
        } catch (DataIntegrityViolationException e) {
            throw new FriendException(BLOCK_OFF_NON_EXISTENT_USER);
        }

        String msg = String.format("%s 유저를 차단했습니다.", opponentID);

        return new BaseResponse(200, msg, null);
    }

    @Override
    public BaseResponse unblockOffFriend(String memberID, String opponentID) {

        // 자기 자신을 차단 해제할 경우
        if (memberID.equals(opponentID)) {
            throw new FriendException(UNBLOCK_OFF_MYSELF);
        }

        // 존재하지 않는 유저를 차단 해제할 경우
        try {
            friendMapper.blockOrUnblockOffFriend(memberID, opponentID, 0);
        } catch (DataIntegrityViolationException e) {
            throw new FriendException(UNBLOCK_OFF_NON_EXISTENT_USER);
        }

        String msg = String.format("%s 유저를 차단 해제했습니다.", opponentID);

        return new BaseResponse(200, msg, null);
    }

    @Override
    public List<FriendInfoDTO> getBlockList(String memberID) {
        return friendMapper.getBlockOffOrFriendInfoList(memberID, 1);
    }
}
