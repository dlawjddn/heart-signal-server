package com.heartsignal.dev.dto.team.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SignalTeamsDTO {
    private List<TeamDTO> teams;

    public SignalTeamsDTO(List<TeamDTO> teams) {
        this.teams = teams;
    }
}
