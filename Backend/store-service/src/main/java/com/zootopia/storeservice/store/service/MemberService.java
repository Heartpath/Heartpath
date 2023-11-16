package com.zootopia.storeservice.store.service;

import com.zootopia.storeservice.common.error.code.ErrorCode;
import com.zootopia.storeservice.common.error.exception.BadRequestException;
import com.zootopia.storeservice.common.error.exception.ServerException;
import com.zootopia.storeservice.store.dto.response.UserResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class MemberService {
//    public UserResDto accessTokenToMember(String accessToken) {
//        WebClient webClient = WebClient.builder().build();
//
//        UserResDto res = webClient.get()
//                .uri("http://3.34.86.93/api/user")
//                .header(HttpHeaders.AUTHORIZATION, accessToken)
//                .retrieve() // ResponseEntity를 받아 디코딩, exchange() : ClientResponse를 상태값, 헤더 제공
//                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
//                    throw new BadRequestException(ErrorCode.INVALID_USER_REQUEST);
//                })
//                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
//                    throw new ServerException(ErrorCode.UNSTABLE_SERVER);
//                })
//                .bodyToMono(UserResDto.class)
//                .block();
//
//        return res;
//    }
    public UserResDto accessTokenToMember(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String apiUrl = "http://3.34.86.93/api/user";

        try {
            ResponseEntity<UserResDto> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, UserResDto.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException.Unauthorized unauthorizedException) {
            // Handle Unauthorized (401) error
            throw new BadRequestException(ErrorCode.EXPIRED_TOKEN);
        }
    }

//    public void pointToMember(String memberId, int point){
//        WebClient webClient = WebClient.builder().build();
//        log.info("들어옴?");
//
//        webClient.post()
//                .uri("http://3.34.86.93/api/point/{memberId}/{point}", memberId, point)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//        log.info("보내짐??");
//    }
    public void pointToMember(String memberId, int point) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.info("resttemplate는 되나요?");

        String url = "http://3.34.86.93/api/point/{memberId}/{point}";

        HttpEntity<String> request = new HttpEntity<>(headers);
        log.info("이 위에도 찍혀?");
        restTemplate.postForObject(url, request, String.class, memberId, point);
        log.info("보내짐??");
    }
}
