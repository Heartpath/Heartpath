package com.zootopia.userservice.service;

import com.zootopia.userservice.domain.User;
import com.zootopia.userservice.dto.FriendShipDTO;
import com.zootopia.userservice.dto.UserInfoDTO;
import com.zootopia.userservice.jwt.JwtProvider;
import com.zootopia.userservice.mapper.FriendMapper;
import com.zootopia.userservice.mapper.UserMapper;
import com.zootopia.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class APIServiceImpl implements APIService {

    private final JwtProvider jwtProvider;
    private final FriendMapper friendMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO loadUserInfo(String token) {

        String memberIDFromToken = jwtProvider.getMemberIDFromToken(token);
        if (memberIDFromToken.isEmpty()) {
            return null;
        }

        Optional<User> findUser = userRepository.findByMemberID(memberIDFromToken);
        if (findUser.isEmpty()) {
            return null;
        }

        User user = findUser.get();

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.ofEntity(user);

        return userInfoDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO loadUserInfoByMemberID(String memberID) {
        return userMapper.findByMemberID(memberID);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendShipDTO> checkRelationshipWithFriends(String from, String to) {
        List<FriendShipDTO> relationshipWithFriendsDTO = friendMapper.getRelationshipWithFriends(from, to);
        return relationshipWithFriendsDTO;
    }
}
