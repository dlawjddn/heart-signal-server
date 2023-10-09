package com.heartsignal.dev.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @PatchMapping("/api/v1/auth/refresh")
    public ResponseEntity<Void> returnRefreshToken(){
        return ResponseEntity.ok().build();
    }

}
