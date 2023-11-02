package com.zootopia.letterservice.letter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "PLACE_IMAGE")
@NoArgsConstructor
public class PlaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "PLACE_IMAGE_ID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LETTER_ID", nullable = false)
    private LetterMySQL letter;

    @Column(length = 1000, name = "IMAGE_PATH")
    private String imagePath;

    @Builder
    public PlaceImage(LetterMySQL letter, String imagePath) {
        this.letter = letter;
        this.imagePath = imagePath;
    }
}
