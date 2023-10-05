package com.heartsignal.dev.dto.chat.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String sender;
    private String content;
    private String sendTime;
}
