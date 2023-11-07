package com.zootopia.letterservice.letter.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "LETTER")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LetterMySQL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "LETTER_ID")
    private Long id;

    @Column(name = "SENDER_ID")
    private String senderId;

    @Column(name = "RECEIVER_ID")
    private String receiverId;

    @Column(name="CONTENT")
    private String content;

    @Column(nullable = false, columnDefinition = "boolean default true", name = "IS_PLACE")
    private boolean isPlace;

    @Column(nullable = false, columnDefinition = "boolean default false", name = "IS_READ")
    private boolean isRead;

    @Column(nullable = false, name = "IS_BLOCKED")
    private boolean isBlocked;

    @Column(name = "CREATED_DATE")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(nullable = false, name = "TYPE")
    private String type;

    @Column(nullable = false, columnDefinition = "boolean default false", name = "SENDER_IS_DELETED")
    private boolean senderIsDeleted;

    @Column(nullable = false, columnDefinition = "boolean default false", name = "RECEIVER_IS_DELETED")
    private boolean ReceiverIsDeleted;

    @Column(name = "LAT")
    private Double lat;

    @Column(name = "LNG")
    private Double lng;

    @OneToMany(mappedBy = "letter", cascade =  CascadeType.ALL, orphanRemoval = true)
    private List<LetterImage> letterImages;

    @OneToMany(mappedBy = "letter", cascade =  CascadeType.ALL, orphanRemoval = true)
    private List<PlaceImage> placeImages;

    @Builder
    public LetterMySQL(String content, String type, Double lat, Double lng) {
        this.content = content;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.isPlace = true;
    }

}
