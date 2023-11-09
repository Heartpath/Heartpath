package com.zootopia.storeservice.store.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "CROW_TIT_BOOK")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CrowTitBookId.class)
public class CrowTitBook {

    @Id
    @Column(name = "CROW_TIT_ID")
    private Long crowtitId;

    @Id
    @Column(name = "MEMBER_ID")
    private String memberId;
    @Column(name = "IS_MAIN")
    private Boolean isMain;
    @Column(name = "ACQUISITION_DATE")
    private LocalDateTime acquisitionDate;


}
