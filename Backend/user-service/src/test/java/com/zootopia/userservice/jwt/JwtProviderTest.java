package com.zootopia.userservice.jwt;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
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

    @Test
    void validateToken() {

        String accessToken = jwtProvider.createAccessToken(memberID);

//        boolean res = jwtProvider.validateToken(accessToken);
//        Assertions.assertTrue(res);
    }

//    @Test
//    void validateExpiredToken() {
//
//        String accessToken = jwtProvider.createAccessToken(memberID);
//
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            System.out.println("error = " + e);
//        }
//
//        boolean res = jwtProvider.validateToken(accessToken);
//        Assertions.assertFalse(res);
//    }
}