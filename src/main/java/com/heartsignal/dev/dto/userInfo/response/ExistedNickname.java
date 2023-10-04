package com.heartsignal.dev.dto.userInfo.response;

import lombok.Data;

@Data
public class ExistedNickname {
    private boolean isExisted;

    public ExistedNickname(boolean isExisted) {
        this.isExisted = isExisted;
    }
}
