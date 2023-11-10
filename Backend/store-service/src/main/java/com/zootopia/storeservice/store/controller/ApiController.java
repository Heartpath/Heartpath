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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {

    private final MemberService memberService;
    private final ApiService apiService;

    @PostMapping("/default/{memberId}")
    @Operation(summary = "기본 캐릭터 설정", description = "Authorization : Bearer {accessToken}, 필수")
    public ResponseEntity<? extends BaseResponseBody> setDefaultCrowtit(@PathVariable String memberId){

        apiService.setDefaultCrowTit(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "기본 캐릭터 저장 성공"));
    }
}
