package com.zootopia.storeservice.store.controller;

import com.zootopia.storeservice.common.dto.BaseResponseBody;
import com.zootopia.storeservice.store.dto.response.UserResDto;
import com.zootopia.storeservice.store.service.ApiService;
import com.zootopia.storeservice.store.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {

    private final MemberService memberService;
    private final ApiService apiService;

    @PostMapping("/default")
    @Operation(summary = "기본 캐릭터 설정", description = "Authorization : Bearer {accessToken}, 필수")
    public ResponseEntity<? extends BaseResponseBody> setDefaultCrowtit(@RequestHeader(value = "Authorization") String accessToken){
        UserResDto userResDto = memberService.accessTokenToMember(accessToken);
        String memberId = userResDto.getData().getMemberID();

        apiService.setDefaultCrowTit(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "기본 캐릭터 저장 성공"));
    }
}
