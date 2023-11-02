package com.zootopia.storeservice.store.service;

import com.zootopia.storeservice.store.dto.request.PointTransReqDto;
import com.zootopia.storeservice.store.entity.Point;
import com.zootopia.storeservice.store.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{

    private final PointRepository pointRepository;

    @Override
    public List<Point> getPointUsage(String memberId){
        List<Point> pointUsage = pointRepository.findByMemberId(memberId);
        return pointUsage;
    }
    @Override
    public void transactionPoint(String memberId, PointTransReqDto pointTransReqDto){
        List<Point> pointUsage = pointRepository.findByMemberId(memberId);
//        int lastBalance =
    }
}
