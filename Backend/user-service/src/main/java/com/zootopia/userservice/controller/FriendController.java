package com.zootopia.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class FriendController {

    private String memberID;

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

}
