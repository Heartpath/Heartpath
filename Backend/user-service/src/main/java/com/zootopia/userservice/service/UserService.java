package com.zootopia.userservice.service;

import com.zootopia.userservice.dto.MypageDTO;
import com.zootopia.userservice.dto.UserRegisterDTO;
import com.zootopia.userservice.dto.UserSearchDTO;
import com.zootopia.userservice.exception.JwtException;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;


public interface UserService {

    MypageDTO loadUserInfo(String memberID);

    boolean checkIfDuplicatedUserID(String memberID);

    HashMap<String, String> registerUser(UserRegisterDTO userRegisterDTO);

    String reissueAccessToken(String refreshToken) throws JwtException;

    List<UserSearchDTO> searchUser(String query, int limit, String memberID, boolean checkFriends);

    HashMap<String, String> reviseUserInfo(MultipartFile userProfileImage, String nickname, String memberID);
}
