package com.zootopia.letterservice.letter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "LETTER_IMAGE")
@NoArgsConstructor
@AllArgsConstructor
public class LetterImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "LETTER_IMAGE_ID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LETTER_ID", nullable = false)
    private LetterMySQL letter;

    @Column(length = 1000, name = "IMAGE_PATH")
    private String imagePath;

    @Builder
    public LetterImage(LetterMySQL letter, String imagePath) {
        this.letter = letter;
        this.imagePath = imagePath;
    }

}