package com.heartsignal.dev.service.domain.rds;

import com.heartsignal.dev.repository.rds.BarChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BarChatroomService {

    private final BarChatRoomRepository barChatroomRepository;
}
