package com.zootopia.letterservice.letter.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendReqDto {
    private String senderId;
    private String receiverId;
}
