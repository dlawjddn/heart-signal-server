package com.heartsignal.dev.dto.team.response;

import lombok.Data;

@Data
public class TeamDTO {
    private Long teamId;
    private String teamName;

    public TeamDTO(Long teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }
}
