package com.heartsignal.dev.service.domain.rds;


import com.heartsignal.dev.domain.rds.MeetingChatRoom;
import com.heartsignal.dev.domain.rds.Team;
import com.heartsignal.dev.repository.rds.MeetingChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingChatRoomService {

    private final MeetingChatRoomRepository meetingChatRoomRepository;

    @Transactional
    public Long makeMeetingChatRoom(Team team1, Team team2) {
        MeetingChatRoom meetingChatRoom = MeetingChatRoom.builder()
                .team1(team1)
                .team2(team2)
                .build();
        return meetingChatRoomRepository.save(meetingChatRoom).getId();
    }
}
