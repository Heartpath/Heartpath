package com.zootopia.storeservice.store.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "POINT_TRANSACTION")
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id
    @Column(name = "POINT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEMBER_ID")
    private String memberId;
    @Column(name = "OUTLINE")
    private String outline;
    @Column(name = "PRICE")
    private int price;
    @Column(name = "BALANCE")
    private int balance;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Builder
    public Point(String memberId, String outline, int price, int balance, LocalDateTime createdDate){
        this.memberId = memberId;
        this.outline = outline;
        this.price = price;
        this.balance = balance;
        this.createdDate = createdDate;
    }
}
