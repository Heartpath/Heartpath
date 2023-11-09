package com.zootopia.letterservice.letter.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserResDto {
    private int status;
    private String message;
    private UserDetailResDto data;
}
