package com.zootopia.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zootopia.userservice.domain.User;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDTO {

    private String memberID;

    private String fcmToken;

    private String nickname;

    private String profileImagePath;

    private int point;

    private LocalDateTime createdDateTime;
    
    public void ofEntity(User user) {
        this.memberID = user.getMemberID();
        this.fcmToken = user.getFcmToken();
        this.nickname = user.getNickname();
        this.profileImagePath = user.getProfileImagePath();
        this.point = user.getPoint();
        this.createdDateTime = user.getCreatedDate();
    }
}
