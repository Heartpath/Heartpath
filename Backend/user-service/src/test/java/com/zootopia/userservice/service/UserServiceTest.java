package com.zootopia.userservice.service;

import com.zootopia.userservice.dto.UserInfoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    final String memberID = "MEMBER_ID";

    @Autowired
    UserService userService;

    @Test
    @DisplayName("유저_정보_조회")
    void loadUserInfo() {
        UserInfoDTO userInfoDTO = userService.loadUserInfo(memberID);
        Assertions.assertEquals(userInfoDTO.getCharacterImagePath(), "CROW_TIT_IMAGE_PATH");
    }
}