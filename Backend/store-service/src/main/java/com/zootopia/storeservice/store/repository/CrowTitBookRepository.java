package com.zootopia.storeservice.store.repository;

import com.zootopia.storeservice.store.entity.CrowTitBook;
import com.zootopia.storeservice.store.entity.CrowTitBookId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrowTitBookRepository extends JpaRepository<CrowTitBook, CrowTitBookId> {

    List<CrowTitBook> findAllByMemberId(String memberId);

    Optional<CrowTitBook> findByMemberIdAndIsMain(String memberId, boolean isMain);

    Optional<CrowTitBook> findById(CrowTitBookId crowTitId);

    Optional<CrowTitBook> findByCrowTitIdAndMemberId(int crowTitBookId, String memberId);

}
