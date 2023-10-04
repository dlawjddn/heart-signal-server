package com.heartsignal.dev.dto.team.request;

import lombok.Data;

import java.util.List;

@Data
public class SaveTeamInfo {
    private List<String> nicknames;
    private String title;
}
