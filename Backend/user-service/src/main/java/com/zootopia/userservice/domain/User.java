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

    @Column(name = "kakao_id")
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
}
