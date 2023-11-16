package com.zootopia.userservice.service;

import com.zootopia.userservice.domain.User;
import com.zootopia.userservice.dto.UserPointTXDTO;
import com.zootopia.userservice.mapper.PointMapper;
import com.zootopia.userservice.mapper.UserMapper;
import com.zootopia.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final UserMapper userMapper;
    private final PointMapper pointMapper;
    private final UserRepository userRepository;

    @Override
    public List<UserPointTXDTO> loadUserPointTransaction(String memberID) {
        return pointMapper.readUserPointTransaction(memberID);
    }

    @Override
    public int reviseUserPoint(String memberID, int point) {
//        return userMapper.updateUserPoint(memberID, point);
        User findMemberID = userRepository.findByMemberID(memberID).get();
        findMemberID.setPoint(point);
        userRepository.save(findMemberID);

        return 1;
    }
}
