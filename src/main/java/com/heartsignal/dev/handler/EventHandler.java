package com.heartsignal.dev.handler;

import com.heartsignal.dev.dto.chat.response.MessageDTO;
import com.heartsignal.dev.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class EventHandler {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @EventListener(Event.class)
    public void handle(Event event) {
        simpMessagingTemplate.convertAndSend("/subscribe/rooms/" + event.getId(),
                MessageDTO.builder()
                        .sender("관리자 봇")
                        .content(event.getNickname() +"님이 나가셨습니다! 방이 폭파됩니다!")
                        .sendTime(OffsetDateTime.now().toString())
                        .build());

    }
}
