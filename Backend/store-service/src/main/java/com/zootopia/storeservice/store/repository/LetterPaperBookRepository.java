package com.zootopia.storeservice.store.repository;

import com.zootopia.storeservice.store.entity.LetterPaperBook;
import com.zootopia.storeservice.store.entity.LetterPaperBookId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LetterPaperBookRepository extends JpaRepository<LetterPaperBook, LetterPaperBookId> {

    List<LetterPaperBook> findAllByMemberId(String memberId);

    Optional<LetterPaperBook> findById(LetterPaperBookId letterPaperBookId);
}
