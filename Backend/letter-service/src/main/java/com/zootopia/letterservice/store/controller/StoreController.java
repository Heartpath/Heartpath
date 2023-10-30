package com.zootopia.letterservice.store.controller;

import com.zootopia.letterservice.common.dto.BaseResponseBody;
import com.zootopia.letterservice.store.dto.request.CharacterBuyReqDto;
import com.zootopia.letterservice.store.dto.request.LetterPaperBuyReqDto;
import com.zootopia.letterservice.store.entity.CrowTit;
import com.zootopia.letterservice.store.entity.LetterPaper;
import com.zootopia.letterservice.store.service.StoreService;
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

    private final StoreService storeService;

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
    @GetMapping("/letterpaper/{letterpaper_id}")
    public ResponseEntity<? extends BaseResponseBody> getLetterPaper(@RequestHeader("Authorization") String accessToken,
                                                                     @PathVariable(value = "letterpaper_id") Long letterpaper_id){

        LetterPaper letterPaperInfo = storeService.getLetterPaperDetail(letterpaper_id);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "편지지 상세 조회 성공", letterPaperInfo));
    }

    // 편지지 구매
    @PostMapping("/letterpaper/buy")
    public ResponseEntity<? extends BaseResponseBody> buyLetterPaper(@RequestHeader("Authorization") String accessToken,
                                                                     @RequestBody LetterPaperBuyReqDto letterPaperBuyReqDto){

//        String memberId = webClient
//            .post()
//            .header("Authorization", accessToken)
//            .retrieve()
//            .bodyToMono(Object.class.getId());
//
//        storeService.buyLetterPaper(memberId, letterPaperBuyReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "편지 구매 성공"));
    }


    // ########################################################


    // 캐릭터 목록 조회
    @GetMapping("/character")
    public ResponseEntity<? extends BaseResponseBody> getCharacterList(@RequestHeader("Authorization") String accessToken){
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "캐릭터 목록 조회 성공"));
    }

    // 캐릭터 상세 조회
    @GetMapping("/character/{charater_id}")
    public ResponseEntity<? extends BaseResponseBody> getCharacter(@RequestHeader("Authorization") String accessToken,
                                                                   @PathVariable("charater_id") Long charater_id){

        CrowTit crowTitInfo = storeService.getCharacterInfo(charater_id);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseBody<>(200, "캐릭터 상세 조회 성공", crowTitInfo));
    }

    // 캐릭터 구매
    @PostMapping("/charater/buy")
    public ResponseEntity<? extends BaseResponseBody> buyCharacter(@RequestHeader("Authorization") String accessToken,
                                                                   @RequestBody CharacterBuyReqDto characterBuyReqDto){

//        String memberId = webClient
//            .post()
//            .header("Authorization", accessToken)
//            .retrieve()
//            .bodyToMono(Object.class.getId());
//
//        storeService.buyCharacter(memberId, characterBuyReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseBody<>(201, "캐릭터 구매 성공"));
    }

}
