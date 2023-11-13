package com.zootopia.letterservice.letter.repository;

import com.zootopia.letterservice.letter.entity.LetterMySQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LetterJpaRepository extends JpaRepository<LetterMySQL, Long> {
    List<LetterMySQL> findBySenderId(String memberId);

    List<LetterMySQL> findByReceiverIdAndIsPickup(String memberId, boolean isPickup);

    @Modifying
    @Query("UPDATE LetterMySQL l SET l.isRead = true WHERE l.id = :letterId")
    void setLetterIsReadTrue(Long letterId);
}
