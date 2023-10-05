package com.heartsignal.dev.dto.userInfo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdditionalInfoDTO {
    private String nickname;
    private String mbti;
    private String face;
    private String selfInfo;
}
