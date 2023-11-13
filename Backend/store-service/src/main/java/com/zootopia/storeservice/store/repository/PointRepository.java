package com.zootopia.storeservice.store.repository;

import com.zootopia.storeservice.store.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Integer> {

    List<Point> findByMemberId(String memberId);

}
