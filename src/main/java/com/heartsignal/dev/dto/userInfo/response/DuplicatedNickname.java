package com.heartsignal.dev.dto.userInfo.response;

import lombok.Data;

@Data
public class DuplicatedNickname {
    private boolean isDuplicated;

    public DuplicatedNickname(boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }
}
