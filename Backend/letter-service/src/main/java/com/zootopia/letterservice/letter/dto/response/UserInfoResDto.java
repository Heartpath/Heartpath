package com.zootopia.letterservice.letter.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoResDto {
    private int status;
    private String message;
    private UserInfoDetailResDto data;
}
