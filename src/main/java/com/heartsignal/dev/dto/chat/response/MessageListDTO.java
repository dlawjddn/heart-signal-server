package com.heartsignal.dev.dto.chat.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageListDTO {

    private String id;
    private List<MessageDTO> messageList;
}
