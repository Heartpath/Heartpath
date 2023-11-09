package com.zootopia.storeservice.store.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "CROW_TIT_BOOK")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrowTitBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CROW_TIT_ID")
    private CrowTit crowTit;

    @Column(name = "MEMBER_ID")
    private String memberId;
    @Column(name = "IS_MAIN")
    private Boolean isMain;
    @Column(name = "ACQUISITION_DATE")
    private LocalDateTime acquisitionDate;
}
