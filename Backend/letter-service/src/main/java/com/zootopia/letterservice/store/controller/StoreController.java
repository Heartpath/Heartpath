package com.zootopia.letterservice.store.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store")
public class StoreController {

//    // 편지지 목록 조회
//    @GetMapping("/letterpaper")
//    public ResponseEntity<?> getLetterPaperList(){
//        return ResponseEntity.ok();
//    }
//
//    // 편지지 상세 조회
//    @GetMapping("/letterpaper")
//    public ResponseEntity<?> getLetterPaper(@RequestParam(value = "letterpaper_id") Long letterpaper_id){
//        return ResponseEntity.ok();
//    }
//
//    // 편지지 구매
//    @PostMapping("/letterpaper/buy")
//    public ResponseEntity<?> buyLetterPaper(){
//        return ResponseEntity.ok();
//    }
//
//
//    // ########################################################
//
//
//    // 캐릭터 목록 조회
//    @GetMapping("/character")
//    public ResponseEntity<?> getCharacterList(){
//        return ResponseEntity.ok();
//    }
//
//    // 캐릭터 상세 조회
//    @GetMapping("/character")
//    public ResponseEntity<?> getCharacter(@RequestParam("charater_id") Long charater_id){
//        return ResponseEntity.ok();
//    }
//
//    // 캐릭터 구매
//    @PostMapping("/charater/buy")
//    public ResponseEntity<?> buyCharacter(){
//        return ResponseEntity.ok();
//    }

}
