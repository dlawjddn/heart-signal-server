package com.heartsignal.dev.service.domain.nosql;

import com.heartsignal.dev.domain.nosql.Chat;
import com.heartsignal.dev.exception.custom.CustomException;
import com.heartsignal.dev.exception.custom.ErrorCode;
import com.heartsignal.dev.repository.nosql.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public Chat findChatById(String barId){

        return chatRepository.findById(barId).orElseThrow(() -> new CustomException(ErrorCode.CHAT_NOT_FOUND));
    }

    public void saveChat(Long id) {
        Chat chat = Chat.builder()
                .id(id.toString())
                .build();
        chatRepository.save(chat);
    }

    public void deleteChat(Long roomId) {
        chatRepository.deleteById(roomId.toString());
    }
}
