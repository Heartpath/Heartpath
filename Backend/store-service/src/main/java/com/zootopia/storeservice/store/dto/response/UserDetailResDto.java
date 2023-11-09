package com.zootopia.storeservice.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailResDto {
    private String memberID;
    private String fcmToken;
    private String nickname;
    private String profileImagePath;
    private int point;
    private LocalDateTime createdDateTime;
}
