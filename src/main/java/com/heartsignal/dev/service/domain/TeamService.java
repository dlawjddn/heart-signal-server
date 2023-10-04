package com.heartsignal.dev.service.domain;


import com.heartsignal.dev.domain.Team;
import com.heartsignal.dev.domain.User;
import com.heartsignal.dev.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    @Transactional
    public void saveTeam(User leader, List<User> members, String title){
        teamRepository.save(new Team(leader, title, members));
        log.info("팀 구성 완료");
    }
}
