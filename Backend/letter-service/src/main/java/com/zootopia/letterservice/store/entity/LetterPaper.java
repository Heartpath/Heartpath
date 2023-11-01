package com.zootopia.letterservice.store.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "LETTER_PAPER")
@NoArgsConstructor
@AllArgsConstructor
public class LetterPaper {

    @Id
    @Column(name = "letter_paper_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long price;
    private String description;
    private String imagePath;

    @Builder
    public LetterPaper(String name, Long price, String description, String imagePath){
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
    }
}
