package com.heartsignal.dev.dto.userInfo.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPageDTO {
    private String nickname;
    private String mbti;
    private String face;
    private String selfInfo;
    private int matchStatus;
}
