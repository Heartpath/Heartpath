package com.zootopia.letterservice.letter.repository;

import com.zootopia.letterservice.letter.entity.LetterMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LetterMongoRepository extends MongoRepository<LetterMongo, String> {
    List<LetterMongo> findBySenderId(String senderId);
}
