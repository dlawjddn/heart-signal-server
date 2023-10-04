package com.heartsignal.dev.controller;

import com.heartsignal.dev.Facade.AggregationFacade;
import com.heartsignal.dev.domain.User;
import com.heartsignal.dev.dto.signal.response.SignalDTO;
import com.heartsignal.dev.dto.team.response.SignalTeamsInfo;
import com.heartsignal.dev.dto.team.request.SaveTeamInfo;
import com.heartsignal.dev.dto.team.response.TeamDetailsDTO;
import com.heartsignal.dev.oauth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public SignalTeamsInfo signalList(@AuthenticationPrincipal PrincipalDetails principalDetails){
        User leader = principalDetails.getUser();
        return aggregationFacade.provideSignalList(leader);
    }
    @GetMapping("/{teamId}")
    public TeamDetailsDTO showSignalDetails(@PathVariable Long teamId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        return aggregationFacade.provideTeamDetails(user, teamId);
    }
    @PostMapping("/{teamId}/signal")
    public void sendSignal(@PathVariable Long teamId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        aggregationFacade.sendSignal(user, teamId);
    }
    @PostMapping("/match/confirm")
    public SignalDTO checkMatching(@AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        return aggregationFacade.checkMatching(user);
    }
}
