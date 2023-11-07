package com.zootopia.userservice.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class JwtProviderTest {

    @Autowired
    JwtProvider jwtProvider;

    final String memberID = "ssafy";
    @Test
    void createAccessToken() {
        String accessToken = jwtProvider.createAccessToken(memberID);
        System.out.println("accessToken = " + accessToken);
    }

    @Test
    void createRefreshToken() {
        String refreshToken = jwtProvider.createRefreshToken(memberID);
        System.out.println("refreshToken = " + refreshToken);
    }
}