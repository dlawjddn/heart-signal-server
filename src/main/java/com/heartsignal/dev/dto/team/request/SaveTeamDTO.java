package com.heartsignal.dev.dto.team.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveTeamDTO {
    private List<String> nicknames;
    private String title;
}
