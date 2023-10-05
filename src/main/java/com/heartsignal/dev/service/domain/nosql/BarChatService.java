package com.heartsignal.dev.service.domain.nosql;

import com.heartsignal.dev.domain.nosql.BarChat;
import com.heartsignal.dev.exception.custom.CustomException;
import com.heartsignal.dev.exception.custom.ErrorCode;
import com.heartsignal.dev.repository.nosql.BarChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BarChatService {

    private final BarChatRepository barChatRepository;


    public BarChat findBarChat(String barId) {
        return barChatRepository.findById(barId).orElseThrow(() -> new CustomException(ErrorCode.BAR_CHAT_NOT_FOUND));
    }
}
