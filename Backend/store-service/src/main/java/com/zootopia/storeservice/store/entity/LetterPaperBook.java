package com.zootopia.storeservice.store.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Table(name = "LETTER_PAPER_BOOK")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LetterPaperBookId.class)
public class LetterPaperBook {


    @Id
    @Column(name = "LETTER_PAPER_ID")
    private int letterPaperId;

    @Id
    @Column(name = "MEMBER_ID")
    private String memberId;
    @Column(name = "ACQUISITION_DATE")
    private LocalDateTime acquisitionDate;


}
