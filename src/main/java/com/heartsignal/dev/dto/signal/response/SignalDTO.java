package com.heartsignal.dev.dto.signal.response;

import com.heartsignal.dev.dto.team.response.TeamDTO;

import java.util.List;

public class SignalDTO {
    List<TeamDTO> sendingSignal;
    List<TeamDTO> receivedSignal;

    public SignalDTO(List<TeamDTO> sendingSignal, List<TeamDTO> receivedSignal) {
        this.sendingSignal = sendingSignal;
        this.receivedSignal = receivedSignal;
    }
}
