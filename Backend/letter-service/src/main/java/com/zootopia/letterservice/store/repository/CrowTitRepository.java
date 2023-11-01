package com.zootopia.letterservice.store.repository;

import com.zootopia.letterservice.store.entity.CrowTit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrowTitRepository extends JpaRepository<CrowTit, Long> {

    Optional<CrowTit> findById(Long crow_tit_id);
}
