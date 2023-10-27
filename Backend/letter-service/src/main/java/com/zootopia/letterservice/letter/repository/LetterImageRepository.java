package com.zootopia.letterservice.letter.repository;

import com.zootopia.letterservice.letter.entity.LetterImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterImageRepository extends JpaRepository<LetterImage, Long> {
}
