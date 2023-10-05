package com.heartsignal.dev.service.domain;

import com.heartsignal.dev.repository.rds.BarChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BarChatroomService {

    private final BarChatRepository barChatroomRepository;
}
