package com.zootopia.storeservice.store.repository;

import com.zootopia.storeservice.store.entity.CrowTit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrowTitRepository extends JpaRepository<CrowTit, Long> {

    Optional<CrowTit> findById(Long crowtitId);
}
