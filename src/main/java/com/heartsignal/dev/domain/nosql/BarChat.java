package com.heartsignal.dev.domain.nosql;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bar_chats")
public class BarChat {
    @Id
    private Integer id;     //각 주점별 Id;
    private List<Message> messages;

}