package com.zootopia.letterservice.letter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class PlaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "place_image_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id", nullable = false)
    private LetterMySQL letter;

    @Column(length = 1000)
    private String imagePath;

    @Builder
    public PlaceImage(LetterMySQL letter, String imagePath) {
        this.letter = letter;
        this.imagePath = imagePath;
    }
}
