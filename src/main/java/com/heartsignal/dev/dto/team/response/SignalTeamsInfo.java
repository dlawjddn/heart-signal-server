package com.heartsignal.dev.dto.team.response;

import com.heartsignal.dev.dto.team.response.TeamDTO;
import lombok.Data;

import java.util.List;

@Data
public class SignalTeamsInfo {
    private List<TeamDTO> teams;

    public SignalTeamsInfo(List<TeamDTO> teams) {
        this.teams = teams;
    }
}
