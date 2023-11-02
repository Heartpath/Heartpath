package com.zootopia.storeservice.store.service;

import com.zootopia.storeservice.store.dto.request.PointTransReqDto;
import com.zootopia.storeservice.store.entity.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PointService {
    List<Point> getPointUsage(String memberId);

    void transactionPoint(String memeberId, PointTransReqDto pointTransReqDto);
}
