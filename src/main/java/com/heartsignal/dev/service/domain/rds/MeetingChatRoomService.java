package com.heartsignal.dev.service.domain.rds;

import com.heartsignal.dev.domain.rds.MeetingChatRoom;
import com.heartsignal.dev.domain.rds.Team;
import com.heartsignal.dev.exception.custom.CustomException;
import com.heartsignal.dev.exception.custom.ErrorCode;
import com.heartsignal.dev.repository.rds.MeetingChatRoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MeetingChatRoomService {

    private final EntityManager entityManager;
    private final MeetingChatRoomRepository meetingChatRoomRepository;

    @Transactional
    public Long makeMeetingChatRoom(Team team1, Team team2) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("createMeeting");

        storedProcedure.registerStoredProcedureParameter("v_team1_id", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("v_team2_id", Long.class, ParameterMode.IN);
        storedProcedure.setParameter("v_team1_id", team1.getId());
        storedProcedure.setParameter("v_team2_id", team2.getId());

        storedProcedure.registerStoredProcedureParameter("o_meeting_id", Long.class, ParameterMode.OUT);
        storedProcedure.execute();


        return (Long) storedProcedure.getOutputParameterValue("o_meeting_id");
    }

    public Long findMeetingChatRoomByTeam(Team team) {
        MeetingChatRoom meetingChatRoom = meetingChatRoomRepository.findByTeam1OrTeam2(team).orElseThrow(() -> new CustomException(ErrorCode.CHAT_NOT_FOUND));
        return meetingChatRoom.getId();
    }

    @Transactional
    public void deleteMeetingRoom(Long roomId) {
        meetingChatRoomRepository.deleteById(roomId);
    }
}