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
@Document(collection = "chat")
public class Chat {
    @Id
    private Integer id;
    private List<Message> messages;
}