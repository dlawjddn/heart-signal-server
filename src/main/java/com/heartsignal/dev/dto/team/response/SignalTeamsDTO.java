package com.heartsignal.dev.dto.team.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignalTeamsDTO {
    private List<TeamDTO> teams;
}
