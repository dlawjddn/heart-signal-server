package com.heartsignal.dev.repository.nosql;

import com.heartsignal.dev.domain.nosql.MeetChat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeetChatRepository extends MongoRepository<MeetChat, String> {
}
