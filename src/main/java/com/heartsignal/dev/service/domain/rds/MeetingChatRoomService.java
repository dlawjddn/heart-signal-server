package com.heartsignal.dev.service.domain.rds;

import com.heartsignal.dev.domain.rds.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

@Service
@RequiredArgsConstructor
public class MeetingChatRoomService {

    private final EntityManager entityManager;

    @Transactional
    public Long makeMeetingChatRoom(Team team1, Team team2) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("createMeeting");

        storedProcedure.registerStoredProcedureParameter("v_team1_id", Integer.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("v_team2_id", Integer.class, ParameterMode.IN);
        storedProcedure.setParameter("v_team1_id", team1.getId());
        storedProcedure.setParameter("v_team2_id", team2.getId());

        storedProcedure.registerStoredProcedureParameter("o_meeting_id", Integer.class, ParameterMode.OUT);
        storedProcedure.execute();


        return (Long) storedProcedure.getOutputParameterValue("o_meeting_id");
    }
}