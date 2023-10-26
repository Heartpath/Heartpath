package com.zootopia.letterservice.letter.repository;

import com.zootopia.letterservice.letter.entity.LetterMySQL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterJpaRepository extends JpaRepository<LetterMySQL, Long> {
}
