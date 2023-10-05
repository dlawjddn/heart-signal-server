package com.heartsignal.dev.dto.userInfo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveAdditionalInfo {
    private String nickname;
    private String mbti;
    private String face;
    private String selfInfo;
    private String gender;
}
