package com.heartsignal.dev.dto.userInfo.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainPageDTO {
    private int matchStatus;
    private String nickname;
}
