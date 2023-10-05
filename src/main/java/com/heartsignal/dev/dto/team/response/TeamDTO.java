package com.heartsignal.dev.dto.team.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TeamDTO {
    private Long teamId;
    private String teamName;
}
