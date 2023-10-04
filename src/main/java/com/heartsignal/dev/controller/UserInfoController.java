package com.heartsignal.dev.controller;

import com.heartsignal.dev.Facade.AggregationFacade;
import com.heartsignal.dev.domain.User;
import com.heartsignal.dev.dto.userInfo.DuplicatedNickname;
import com.heartsignal.dev.oauth.PrincipalDetails;
import com.heartsignal.dev.service.domain.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserInfoController {
    private final AggregationFacade aggregationFacade;
    @PostMapping("/duplicate-nickname/{nickname}")
    public DuplicatedNickname checkDuplicatedNickname(@PathVariable String nickname){
        return aggregationFacade.checkDuplicatedNickname(nickname);
    }
}
