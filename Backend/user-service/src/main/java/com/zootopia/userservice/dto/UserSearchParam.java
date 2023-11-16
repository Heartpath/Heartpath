package com.zootopia.userservice.dto;

import lombok.Getter;


@Getter
public class UserSearchParam {

    private String memberID;

    private String query;

    private int limit;

    public UserSearchParam(String query, int limit) {
        this.query = query;
        this.limit = limit;
    }

    public UserSearchParam(String memberID, String query, int limit) {
        this.memberID = memberID;
        this.query = query;
        this.limit = limit;
    }
}
