package com.heartsignal.dev.domain.nosql;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "meet_chats")
public class MeetChat {

    @Id
    private Integer id; // 각 미팅방별 id;
    private List<Message> messages;

}
