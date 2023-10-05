package com.heartsignal.dev.controller;

import com.heartsignal.dev.Facade.AggregationFacade;
import com.heartsignal.dev.domain.rds.User;
import com.heartsignal.dev.dto.userInfo.request.SaveAdditionalInfoDTO;
import com.heartsignal.dev.dto.userInfo.response.AdditionalInfoDTO;
import com.heartsignal.dev.dto.userInfo.response.ExistedNicknameDTO;
import com.heartsignal.dev.oauth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final AggregationFacade aggregationFacade;
    @PostMapping("/additional")
    public void saveAdditionalInfo(@RequestBody SaveAdditionalInfoDTO additionalInfo, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        aggregationFacade.saveAdditionalInfo(user, additionalInfo);
    }
    @GetMapping("/existed-nickname/{nickname}")
    public ExistedNicknameDTO checkDuplicatedNickname(@PathVariable String nickname){
        return aggregationFacade.checkDuplicatedNickname(nickname);
    }
    @GetMapping("/mypage")
    public AdditionalInfoDTO showMyPage(@AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        return aggregationFacade.showMyPage(user);
    }
}
