package com.zootopia.letterservice.store.repository;

import com.zootopia.letterservice.store.entity.LetterPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LetterPaperRepository extends JpaRepository<LetterPaper, Long> {

    Optional<LetterPaper> findById(Long letterpaperId);
}
