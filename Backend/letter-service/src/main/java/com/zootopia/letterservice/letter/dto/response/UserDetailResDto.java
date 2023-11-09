package com.zootopia.letterservice.letter.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailResDto {
    private String memberId;
    private String fcmToken;
    private String nickname;
    private String profileImagePath;
    private int point;
    private LocalDateTime createdDateTime;
}
