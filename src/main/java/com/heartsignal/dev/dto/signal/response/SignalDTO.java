package com.heartsignal.dev.dto.signal.response;

import com.heartsignal.dev.dto.team.response.TeamDTO;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignalDTO {
    List<TeamDTO> sendingSignal;
    List<TeamDTO> receivedSignal;
}
