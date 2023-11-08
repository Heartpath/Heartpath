package com.zootopia.letterservice.letter.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendResDto {
    private Boolean ToFromIsDeleted; // 수신자가 발신자 차단
    private Boolean FromToIsDeleted; // 발신자가 수신자 차단
}
