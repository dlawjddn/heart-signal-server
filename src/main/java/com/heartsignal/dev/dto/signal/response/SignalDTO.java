package com.heartsignal.dev.dto.signal.response;

import com.heartsignal.dev.dto.team.response.TeamDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
public class SignalDTO {
    List<TeamDTO> sendingSignal;
    List<TeamDTO> receivedSignal;
}
