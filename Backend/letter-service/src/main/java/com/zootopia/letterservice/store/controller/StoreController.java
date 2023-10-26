package com.zootopia.letterservice.store.controller;

import com.zootopia.letterservice.common.dto.BaseResponseBody;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store")
public class StoreController {

    @Autowired
    private WebClient webClient;

    // 편지지 목록 조회
    @GetMapping("/letterpaper")
    public ResponseEntity<? extends BaseResponseBody> getLetterPaperList(@RequestHeader("Authorization") String accessToken){

//        String memberId = webClient
//                .post()
//                .header("Authorization", accessToken)
//                .retrieve()
//                .bodyToMono(Object.class.getId());

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "편지지 목록 조회 성공"));
    }

    // 편지지 상세 조회
    @GetMapping("/letterpaper")
    public ResponseEntity<? extends BaseResponseBody> getLetterPaper(@RequestHeader("Autorization") String accessToken,
                                                                     @RequestParam(value = "letterpaper_id") Long letterpaper_id){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "편지지 상세 조회 성공"));
    }

    // 편지지 구매
    @PostMapping("/letterpaper/buy")
    public ResponseEntity<? extends BaseResponseBody> buyLetterPaper(@RequestHeader("Autorization") String accessToken){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "편지 구매 성공"));
    }


    // ########################################################


    // 캐릭터 목록 조회
    @GetMapping("/character")
    public ResponseEntity<? extends BaseResponseBody> getCharacterList(@RequestHeader("Autorization") String accessToken){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "캐릭터 목록 조회 성공"));
    }

    // 캐릭터 상세 조회
    @GetMapping("/character")
    public ResponseEntity<? extends BaseResponseBody> getCharacter(@RequestHeader("Autorization") String accessToken,
                                                                   @RequestParam("charater_id") Long charater_id){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "캐릭터 상세 조회 성공"));
    }

    // 캐릭터 구매
    @PostMapping("/charater/buy")
    public ResponseEntity<? extends BaseResponseBody> buyCharacter(@RequestHeader("Autorization") String accessToken){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "캐릭터 구매 성공"));
    }

}
