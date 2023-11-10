package com.zootopia.storeservice.store.service;

import com.zootopia.storeservice.common.error.code.ErrorCode;
import com.zootopia.storeservice.common.error.exception.BadRequestException;
import com.zootopia.storeservice.common.error.exception.ServerException;
import com.zootopia.storeservice.store.dto.response.UserResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.HttpStatus;

@Service
@Slf4j
public class MemberService {
    public UserResDto accessTokenToMember(String accessToken) {
        WebClient webClient = WebClient.builder().build();

        UserResDto res = webClient.get()
                .uri("http://3.34.86.93/api/user")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .retrieve() // ResponseEntity를 받아 디코딩, exchange() : ClientResponse를 상태값, 헤더 제공
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    throw new BadRequestException(ErrorCode.INVALID_USER_REQUEST);
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    throw new ServerException(ErrorCode.UNSTABLE_SERVER);
                })
                .bodyToMono(UserResDto.class)
                .block();

        return res;
    }

    public String pointToMember(String memberId, int point){
        WebClient webClient = WebClient.builder().build();

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("http://3.34.86.93.api/")
                        .queryParam("{memberId}", memberId)
                        .queryParam("{point}", point)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
