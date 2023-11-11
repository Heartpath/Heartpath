package com.zootopia.userservice.dto;


public class UserSearchDTO {

    private String memberID;

    private String nickname;

    private String profileImagePath;

    private boolean isFriend;

    public String getMemberID() {
        return memberID;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public boolean getIsFriend() {
        return isFriend;
    }
}
