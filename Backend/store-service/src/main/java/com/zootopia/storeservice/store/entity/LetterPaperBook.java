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
public class LetterPaperBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_paper_id")
    private LetterPaper letterPaper;

    @Column(name = "MEMBER_ID")
    private String memberId;
    @Column(name = "ACQUISITION_DATE")
    private LocalDateTime acquisitionDate;

    @Builder
    public LetterPaperBook(LetterPaper letterPaper,LocalDateTime acquisitionDate){
        this.letterPaper = letterPaper;
        this.acquisitionDate = acquisitionDate;
    }
}
