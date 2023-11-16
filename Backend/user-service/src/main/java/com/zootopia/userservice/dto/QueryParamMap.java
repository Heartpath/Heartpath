package com.zootopia.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class QueryParamMap {

    private String query;

    private int limit;
}
