package com.zootopia.letterservice.letter.repository;

import com.zootopia.letterservice.letter.entity.LetterMySQL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterJpaRepository extends JpaRepository<LetterMySQL, Long> {
//    List<LetterMySQL> findBySenderId(String memberId);

//    List<LetterMySQL> findByReceiverId(String memberId);
}
