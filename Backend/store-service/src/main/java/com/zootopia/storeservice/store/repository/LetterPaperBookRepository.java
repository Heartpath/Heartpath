package com.zootopia.storeservice.store.repository;

import com.zootopia.storeservice.store.entity.LetterPaperBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LetterPaperBookRepository extends JpaRepository<LetterPaperBook, Long> {

    List<LetterPaperBook> findAllByMemberId(String memberId);
}
