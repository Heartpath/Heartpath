package com.zootopia.userservice.kakao;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class KakaoUserInfo {

    private Long kakaoId;

    private String nickname;

    private String profileImageURL;

    public void fillDataFromJson(JsonNode jsonNode) {

        if (jsonNode != null) {
            this.kakaoId = jsonNode.get("id").asLong();
            this.nickname = jsonNode.get("properties").get("nickname").asText();
            this.profileImageURL = jsonNode.get("properties").get("profile_image").asText();
        }
    }
}
