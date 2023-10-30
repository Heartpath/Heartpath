package com.zootopia.letterservice.letter.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "letter")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LetterMySQL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "letter_id")
    private Long id;

//    private String senderId;
//    private String receiverId;

    @Column(length = 1000)
    private String content;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isPlace;

    @CreatedDate
    private LocalDateTime CreatedDate;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean senderIsDeleted;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean ReceiverIsDeleted;

    private Double lat;
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
