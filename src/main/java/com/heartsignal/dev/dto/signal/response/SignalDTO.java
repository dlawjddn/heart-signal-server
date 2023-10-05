package com.heartsignal.dev.dto.signal.response;

import com.heartsignal.dev.dto.team.response.TeamDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter
@Setter
public class SignalDTO {
    List<TeamDTO> sendingSignal;
    List<TeamDTO> receivedSignal;
}
