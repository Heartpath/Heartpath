package com.zootopia.storeservice.store.service;

import com.zootopia.storeservice.store.dto.request.PointTransReqDto;
import com.zootopia.storeservice.store.entity.Point;
import com.zootopia.storeservice.store.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
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
        // 포인트 사용 내역을 생성일자(`createdDate`)를 기준으로 내림차순으로 정렬
        pointUsage.sort(Comparator.comparing(Point::getCreatedDate).reversed());
        // 가장 최근의 포인트 사용 내역의 balance 가져오기
        int lastBalance = pointUsage.isEmpty() ? 0 : pointUsage.get(0).getBalance();
        log.warn("최근 포인트 " + lastBalance);

        Point point = Point.builder()
                .memberId(memberId)
                .outline("뱁새 잡기 성공")
                .price(pointTransReqDto.getPoint())
                .balance(lastBalance+pointTransReqDto.getPoint())
                .createdDate(LocalDateTime.now())
                .build();
        pointRepository.save(point);
        log.warn("포인트 저장 성공");
        // member 완성시, 포인트 저장 후 쿼리문 날려서 멤버의 point 변경 필요
    }
}
