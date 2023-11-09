package com.zootopia.letterservice.letter.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Document(collection = "letter_unplaced")
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class LetterMongo {

    @Transient
    public static final String SEQUENCE_NAME = "letter_unplaced";

    @Id
    private String id;

    private String content;

    // 편지 첨부 파일
    private List<String> files;

    @Field("sender_id")
    private String senderId;

    @Field("receiver_id")
    private String receiverId;

    private String type;


    @Builder
    public LetterMongo(String content, List<String> files, String senderId, String receiverId) {
        this.content = content;
        this.files = files;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

}
