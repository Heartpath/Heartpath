package com.zootopia.storeservice.store.repository;

import com.zootopia.storeservice.store.entity.LetterPaper;
import com.zootopia.storeservice.store.entity.LetterPaperBookId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LetterPaperRepository extends JpaRepository<LetterPaper, Integer> {
    Optional<LetterPaper> findById(LetterPaperBookId letterpaperId);
}
