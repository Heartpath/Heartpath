package com.zootopia.letterservice.store.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrowTit {

    @Id
    @Column(name = "crow_tit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long price;
    private String description;
    private String imagePath;

    @Builder
    public CrowTit(String name, Long price, String description, String imagePath){
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
    }
}
