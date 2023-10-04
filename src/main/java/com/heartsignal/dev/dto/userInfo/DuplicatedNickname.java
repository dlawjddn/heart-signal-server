package com.heartsignal.dev.dto.userInfo;

import lombok.Data;

@Data
public class DuplicatedNickname {
    private boolean isDuplicated;

    public DuplicatedNickname(boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }
}
