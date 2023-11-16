package com.zootopia.letterservice.letter.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendResDto {
    private int status;
    private String message;
    private List<FriendDetailResDto> data;
}
