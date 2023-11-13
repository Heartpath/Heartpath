package com.zootopia.userservice.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MEMBER")
public class User {

    @Id
    @Column(name = "MEMBER_ID")
    private String memberID;

    @Column(name = "KAKAO_ID")
    private Long kakaoID;

    @Column(name = "FCM_TOKEN")
    private String fcmToken;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "POINT")
    private int point;

    @Column(name = "PROFILE_IMAGE_PATH")
    private String profileImagePath;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    public void updateFCMToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void updateUserNickname(String nickname) {

        if (nickname != null)
            this.nickname = nickname;
    }

    public void updateProfileImagePath(String profileImagePath) {

        if (profileImagePath != null)
            this.profileImagePath = profileImagePath;
    }
}
