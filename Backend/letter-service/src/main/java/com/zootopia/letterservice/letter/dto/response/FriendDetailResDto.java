package com.zootopia.letterservice.letter.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class FriendDetailResDto {
    private String from;
    private String to;
    private boolean blocked;
}
