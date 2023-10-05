package com.heartsignal.dev.dto.team.response;

import com.heartsignal.dev.dto.userInfo.response.AdditionalInfoDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDetailsDTO {
    private String title;
    private boolean isLeader;
    private List<AdditionalInfoDTO> usersInfo;
}
