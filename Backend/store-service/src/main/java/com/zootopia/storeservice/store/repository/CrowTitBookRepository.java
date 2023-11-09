package com.zootopia.storeservice.store.repository;

import com.zootopia.storeservice.store.entity.CrowTitBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrowTitBookRepository extends JpaRepository<CrowTitBook, Long> {
    List<CrowTitBook> findAllByMemberId(String memberId);
}
