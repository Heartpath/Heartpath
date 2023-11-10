package com.zootopia.storeservice.store.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "LETTER_PAPER")
@NoArgsConstructor
@AllArgsConstructor
public class LetterPaper {

    @Id
    @Column(name = "LETTER_PAPER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NAME")
    private String name;
    @Column(name = "PRICE")
    private int price;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @Builder
    public LetterPaper(String name, int price, String description, String imagePath){
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
    }
}
