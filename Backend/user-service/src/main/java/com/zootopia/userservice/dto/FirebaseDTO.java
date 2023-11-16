package com.zootopia.userservice.dto;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class FirebaseDTO {

    private String targetToken;

    private String title;

    private String body;
}
