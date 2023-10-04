package com.heartsignal.dev.controller;

import com.heartsignal.dev.Facade.AggregationFacade;
import com.heartsignal.dev.domain.User;
import com.heartsignal.dev.dto.team.request.SaveTeamInfo;
import com.heartsignal.dev.oauth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {
    private final AggregationFacade aggregationFacade;
    @PostMapping("/building")
    public void makeTeam(@RequestBody SaveTeamInfo teamInfo, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User leader = principalDetails.getUser();
        aggregationFacade.makeTeam(leader, teamInfo);
    }
}
