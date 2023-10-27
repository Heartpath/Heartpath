package com.zootopia.letterservice.letter.repository;

import com.zootopia.letterservice.letter.entity.LetterMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LetterMongoRepository extends MongoRepository<LetterMongo, String> {
}
