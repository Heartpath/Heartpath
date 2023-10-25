package com.zootopia.letterservice.letter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class LetterImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "letter_image_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id", nullable = false)
    private Letter letter;

    @Column(length = 1000)
    private String imagePath;

    @Builder
    public LetterImage(Letter letter, String imagePath) {
        this.letter = letter;
        this.imagePath = imagePath;
    }

}