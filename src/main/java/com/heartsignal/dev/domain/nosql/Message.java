package com.heartsignal.dev.domain.nosql;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String sender;
    private String content;
    private Date sendAt;

    /**
     * message는 따로 레포, 서비스를 만들지 않음!
     *
     * barChatService, MeetChatService 에서 필요할때 생성하는 걸로!!
     */
}