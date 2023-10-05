package com.heartsignal.dev.dto.team.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignalTeamsDTO {
    private List<TeamDTO> teams;

    public SignalTeamsDTO(List<TeamDTO> teams) {
        this.teams = teams;
    }
}
