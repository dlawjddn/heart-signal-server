package com.heartsignal.dev.dto.team.response;

import com.heartsignal.dev.dto.userInfo.response.AdditionalInfoDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TeamDetailsDTO {
    private String title;
    private boolean isLeader;
    private List<AdditionalInfoDTO> usersInfo;

    public TeamDetailsDTO(String title, boolean isLeader, List<AdditionalInfoDTO> usersInfo) {
        this.title = title;
        this.isLeader = isLeader;
        this.usersInfo = usersInfo;
    }
}
