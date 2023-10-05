package com.heartsignal.dev.controller;

import com.heartsignal.dev.Facade.AggregationFacade;
import com.heartsignal.dev.dto.chat.response.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AggregationFacade aggregationFacade;

    @MessageMapping("/messages/{barId}")
    public void chat(@DestinationVariable String barId, @RequestBody MessageDTO messageDTO) {
        aggregationFacade.saveChat(messageDTO, barId);
        simpMessagingTemplate.convertAndSend("/subscribe/rooms/" + barId, messageDTO);
    }
}
