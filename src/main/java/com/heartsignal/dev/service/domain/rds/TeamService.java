package com.heartsignal.dev.service.domain.rds;


import com.heartsignal.dev.domain.rds.Team;
import com.heartsignal.dev.domain.rds.User;
import com.heartsignal.dev.exception.custom.CustomException;
import com.heartsignal.dev.exception.custom.ErrorCode;
import com.heartsignal.dev.repository.rds.TeamRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final EntityManager entityManager;

    @Transactional
    public void saveTeam(User leader, List<User> members, String title){
        Long leaderId = leader.getId();

        String userNicknames = members.stream()
                                      .map(member -> member.getUserInfo().getNickname()) 
                                      .collect(Collectors.joining(","));

        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("createTeam");
        storedProcedureQuery.registerStoredProcedureParameter("leader_id", Long.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("title", String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("user_nicknames", String.class, ParameterMode.IN);

        storedProcedureQuery.setParameter("leader_id", leaderId);
        storedProcedureQuery.setParameter("title", title);
        storedProcedureQuery.setParameter("user_nicknames", userNicknames);

        try {
            storedProcedureQuery.execute();
            log.info("팀 구성 완료");
        } catch (Exception e) {
            log.error("팀 구성 중 오류 발생", e);
        }
    
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
