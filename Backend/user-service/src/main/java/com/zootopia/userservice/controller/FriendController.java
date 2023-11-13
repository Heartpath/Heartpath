package com.zootopia.userservice.controller;

import com.zootopia.userservice.common.BaseResponse;
import com.zootopia.userservice.dto.FriendInfoDTO;
import com.zootopia.userservice.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "친구 관련 정보 조회 API",
        description = "<b>모든 Method에서 JWT 토큰이 필요합니다.</b>"
)
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    private String memberID;

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    @Operation(summary = "친구 목록 조회 API")
    @ApiResponse(
            responseCode = "200", description = "친구 목록 조회",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "    \"status\": 200,\n" +
                            "    \"message\": \"친구 목록\",\n" +
                            "    \"data\": [\n" +
                            "        {\n" +
                            "            \"memberID\": \"\",\n" +
                            "            \"nickname\": \"\",\n" +
                            "            \"profileImagePath\": \"\"\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"memberID\": \"\",\n" +
                            "            \"nickname\": \"\",\n" +
                            "            \"profileImagePath\": \"\"\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}")
            )
    )
    @GetMapping("/friend")
    public ResponseEntity<BaseResponse> getFriendList() {

        List<FriendInfoDTO> friendInfoList = friendService.getFriendInfoList(memberID);
        for (FriendInfoDTO friendInfoDTO : friendInfoList) {
            System.out.println("friendInfoDTO = " + friendInfoDTO);
        }

        BaseResponse baseResponse = new BaseResponse(200, "친구 목록", friendInfoList);
        return ResponseEntity.status(200).body(baseResponse);
    }

    @Operation(summary = "친구 추가 API, 예외가 발생할 경우 모두 ErrorCode 400입니다!")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400", description = "자기 자신에게 친구 추가 할 경우",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"status\": 1004,\n" +
                                    "    \"message\": \"자기 자신에게 친구 추가를 할 수 없습니다.\",\n" +
                                    "    \"httpStatus\": \"400 BAD_REQUEST\"\n" +
                                    "}")
                    )
            ),
            @ApiResponse(
                    responseCode = "401", description = "이미 친구 관계일 경우",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"status\": 1005,\n" +
                                    "    \"message\": \"이미 친구 추가가 되어있습니다.\",\n" +
                                    "    \"httpStatus\": \"400 BAD_REQUEST\"\n" +
                                    "}")
                    )
            ),
            @ApiResponse(
                    responseCode = "402", description = "존재하지 않은 유저에게 친구 추가 할 경우",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"status\": 1006,\n" +
                                    "    \"message\": \"존재하지 않은 유저에게 친구 추가를 할 수 없습니다.\",\n" +
                                    "    \"httpStatus\": \"400 BAD_REQUEST\"\n" +
                                    "}")
                    )
            ),
            @ApiResponse(
                    responseCode = "200", description = "친구 추가 완료",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"status\": 200,\n" +
                                    "    \"message\": \"친구 추가가 완료되었습니다.\",\n" +
                                    "    \"data\": null\n" +
                                    "}")
                    )
            )
    })
    @PostMapping("/friend/{opponentID}")
    public ResponseEntity<BaseResponse> addFriend(@PathVariable(name = "opponentID") String opponentID) {

        BaseResponse baseResponse = friendService.addFriend(memberID, opponentID);
        return ResponseEntity.status(200).body(baseResponse);
    }

    @Operation(summary = "친구 차단 API, 예외가 발생할 경우 모두 ErrorCode 400입니다!")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400", description = "자기 자신을 차단할 경우",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"status\": 1007,\n" +
                                    "    \"message\": \"자기 자신을 차단할 수 없습니다.\",\n" +
                                    "    \"httpStatus\": \"400 BAD_REQUEST\"\n" +
                                    "}")
                    )
            ),
            @ApiResponse(
                    responseCode = "401", description = "존재하지 않은 유저를 차단할 경우",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"status\": 1008,\n" +
                                    "    \"message\": \"존재하지 않은 유저를 차단 할 수 없습니다.\",\n" +
                                    "    \"httpStatus\": \"400 BAD_REQUEST\"\n" +
                                    "}")
                    )
            ),
            @ApiResponse(
                    responseCode = "200", description = "친구 차단 완료",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"status\": 200,\n" +
                                    "    \"message\": \"ㅇㅇ 유저를 차단했습니다.\",\n" +
                                    "    \"data\": null\n" +
                                    "}")
                    )
            )
    })
    @PutMapping("/friend/{opponentID}")
    public ResponseEntity<BaseResponse> blockFriend(@PathVariable(name = "opponentID") String opponentID) {

        BaseResponse baseResponse = friendService.blockOffFriend(memberID, opponentID);
        return ResponseEntity.status(200).body(baseResponse);
    }

    @Operation(summary = "차단한 유저 목록 조회 API")
    @ApiResponse(
            responseCode = "200", description = "차단한 유저 목록 조회",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "    \"status\": 200,\n" +
                            "    \"message\": \"차단 목록\",\n" +
                            "    \"data\": [\n" +
                            "        {\n" +
                            "            \"memberID\": \"\",\n" +
                            "            \"nickname\": \"\",\n" +
                            "            \"profileImagePath\": \"\"\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"memberID\": \"\",\n" +
                            "            \"nickname\": \"\",\n" +
                            "            \"profileImagePath\": \"\"\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}")
            )
    )
    @GetMapping("/blocklist")
    public ResponseEntity<BaseResponse> listBlockFriends() {

        List<FriendInfoDTO> blockOffFriendList = friendService.getBlockList(memberID);
        BaseResponse baseResponse = new BaseResponse(200, "차단 목록", blockOffFriendList);

        return ResponseEntity.status(200).body(baseResponse);
    }
}
