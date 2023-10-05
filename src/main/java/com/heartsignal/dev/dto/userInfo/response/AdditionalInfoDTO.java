package com.heartsignal.dev.dto.userInfo.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInfoDTO {
    private String nickname;
    private String mbti;
    private String face;
    private String selfInfo;
}
