package com.zootopia.storeservice.store.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "CROW_TIT")
@NoArgsConstructor
@AllArgsConstructor
public class CrowTit {

    @Id
    @Column(name = "CROW_TIT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;
    @Column(name = "PRICE")
    private int price;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @Builder
    public CrowTit(String name, int price, String description, String imagePath){
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
    }
}
