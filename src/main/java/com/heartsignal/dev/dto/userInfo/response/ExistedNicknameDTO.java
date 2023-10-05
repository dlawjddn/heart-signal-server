package com.heartsignal.dev.dto.userInfo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExistedNicknameDTO {
    private boolean isExisted;

    public ExistedNicknameDTO(boolean isExisted) {
        this.isExisted = isExisted;
    }
}
