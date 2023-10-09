package com.heartsignal.dev.controller;

import com.heartsignal.dev.Facade.AggregationFacade;
import com.heartsignal.dev.domain.rds.User;
import com.heartsignal.dev.dto.bar.response.BarListDTO;
import com.heartsignal.dev.dto.chat.response.MessageListDTO;
import com.heartsignal.dev.oauth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bars")
public class BarController {
    private final AggregationFacade aggregationFacade;
    @GetMapping
    public List<BarListDTO> showBars(@AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        return aggregationFacade.provideBarInfos(user);
    }


}
