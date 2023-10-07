package com.heartsignal.dev.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //400

    //401
    ONLY_LEADER(HttpStatus.UNAUTHORIZED, "ONLY LEADER CAN DO"),
    BANNED(HttpStatus.UNAUTHORIZED, "BANNED_USER"),

    //404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER NOT FOUND"),
    USERINFO_NOT_FOUND(HttpStatus.NOT_FOUND, "USERINFO NOT FOUND"),
    NICKNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "NICKNAME NOT FOUND"),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "TEAM NOT FOUND"),
    SIGNAL_NOT_FOUND(HttpStatus.NOT_FOUND, "SIGNAL NOT FOUND"),
    CHAT_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAT NOT FOUND"),
    BAR_NOT_FOUND(HttpStatus.NOT_FOUND, "BAR NOT FOUND"),

    //500

    ;


    private final HttpStatus httpStatus;
    private final String code;

    ErrorCode(HttpStatus httpStatus, String code) {
        this.httpStatus = httpStatus;
        this.code = code;
    }
}
