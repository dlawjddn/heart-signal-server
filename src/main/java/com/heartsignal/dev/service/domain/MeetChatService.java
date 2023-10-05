package com.heartsignal.dev.service.domain;

import com.heartsignal.dev.repository.nosql.MeetChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetChatService {

    private final MeetChatRepository meetChatRepository;


}
