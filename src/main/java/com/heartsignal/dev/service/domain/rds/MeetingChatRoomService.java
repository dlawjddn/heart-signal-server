package com.heartsignal.dev.service.domain.rds;


import com.heartsignal.dev.repository.rds.MeetingChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingChatRoomService {

    private final MeetingChatRoomRepository meetingChatRoomRepository;
}
