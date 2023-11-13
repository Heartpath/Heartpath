package com.zootopia.storeservice.store.repository;

import com.zootopia.storeservice.store.entity.CrowTit;
import com.zootopia.storeservice.store.entity.CrowTitBookId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrowTitRepository extends JpaRepository<CrowTit, Integer> {

    Optional<CrowTit> findById(CrowTitBookId crowtitId);
    List<CrowTit> findAll();
}
