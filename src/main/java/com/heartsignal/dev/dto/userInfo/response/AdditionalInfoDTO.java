package com.heartsignal.dev.dto.userInfo.response;

import lombok.Data;

@Data
public class AdditionalInfoDTO {
    private String nickname;
    private String mbti;
    private String face;
    private String selfInfo;

    public AdditionalInfoDTO(String nickname, String mbti, String face, String selfInfo) {
        this.nickname = nickname;
        this.mbti = mbti;
        this.face = face;
        this.selfInfo = selfInfo;
    }
}
