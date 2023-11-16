package com.zootopia.userservice.service;


import com.zootopia.userservice.dto.UserPointTXDTO;

import java.util.List;

public interface PointService {

    List<UserPointTXDTO> loadUserPointTransaction(String memberID);

    void reviseUserPoint(String memberID, int point);
}
