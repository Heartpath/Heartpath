package com.zootopia.letterservice.letter.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResDto {
    private String memberId;
    private String nickname;
    private String FCMToken;
}
