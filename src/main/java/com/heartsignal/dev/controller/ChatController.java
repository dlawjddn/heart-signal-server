package com.heartsignal.dev.controller;

import com.heartsignal.dev.Facade.AggregationFacade;
import com.heartsignal.dev.domain.rds.User;
import com.heartsignal.dev.dto.chat.response.MeetLeaveStatusDTO;
import com.heartsignal.dev.dto.chat.response.MessageDTO;
import com.heartsignal.dev.dto.chat.response.MessageListDTO;
import com.heartsignal.dev.oauth.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AggregationFacade aggregationFacade;

    @MessageMapping("/messages/{barId}")
    public void chat(@DestinationVariable String barId, @Valid @RequestBody MessageDTO messageDTO) {
        aggregationFacade.saveChat(messageDTO, barId);
        simpMessagingTemplate.convertAndSend("/subscribe/rooms/" + barId, messageDTO);
    }

    @MessageMapping("/delete-room/{meetingRoomId}")
    public void deleteRoom(@DestinationVariable String meetingRoomId, @RequestBody MeetLeaveStatusDTO meetLeaveStatusDTO) {

        log.info("meetLeaveStatus ={} ", meetLeaveStatusDTO.isLeaveClicked());

        simpMessagingTemplate.convertAndSend("/subscribe/delete-room/" + meetingRoomId, meetLeaveStatusDTO);
    }

    @GetMapping("/api/v1/chats/{chatId}/chat")
    public MessageListDTO showBarMessages(@PathVariable String chatId,
                                       @RequestParam
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime enterTime){

        return aggregationFacade.provideBarChatInfos(chatId, enterTime);
    }

    @GetMapping("/api/v1/chats/meeting-room/chat")
    public MessageListDTO showMeetingMessages(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();
        return aggregationFacade.provideMeetingChatInfos(user);
    }

    @DeleteMapping("/api/v1/chats/meeting-room/{roomId}/chat")
    public void deleteMeetingRoom(@PathVariable Long roomId){
        aggregationFacade.deleteMeetingRoom(roomId);
    }

}
