package com.zootopia.storeservice.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResDto {
    private int status;
    private String message;
    private UserDetailResDto data;
}
