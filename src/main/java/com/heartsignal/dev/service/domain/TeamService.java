package com.heartsignal.dev.service.domain;


import com.heartsignal.dev.domain.Team;
import com.heartsignal.dev.domain.User;
import com.heartsignal.dev.exception.custom.CustomException;
import com.heartsignal.dev.exception.custom.ErrorCode;
import com.heartsignal.dev.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    @Transactional
    public void saveTeam(User leader, List<User> members, String title){
        teamRepository.save(
                Team.builder()
                        .leader(leader)
                        .members(members)
                        .title(title)
                        .status(false)
                        .createdAt(new Timestamp(System.currentTimeMillis())).build());
        log.info("팀 구성 완료");
    }
    public List<Team> findSignalList(User leader){
        int memberCnt = leader.getTeam().getMembers().size();
        String teamGender = leader.getUserInfo().getGender();
        return teamRepository.findAll().stream()
                .filter(team -> (team.getLeader() != null && team.getMembers().size() == memberCnt && team.getLeader().getUserInfo().getGender().equals(teamGender)))
                .toList();
    }
    public Team findById(Long teamId){
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));
    }
}
