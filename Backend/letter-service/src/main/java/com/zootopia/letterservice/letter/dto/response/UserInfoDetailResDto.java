package com.zootopia.letterservice.letter.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDetailResDto {
    private String nickname;
    private String fcmToken;
}
