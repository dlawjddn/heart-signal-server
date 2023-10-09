package com.heartsignal.dev.dto.chat.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    @NotNull(message = "보낸이는 필수입니다!")
    private String sender;

    @NotNull(message = "내용은 필수입니다!")
    private String content;

    @NotNull(message = "보낸시간은 필수입니다!")
    private String sendTime;
}
