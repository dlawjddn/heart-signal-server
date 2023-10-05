package com.heartsignal.dev.repository.nosql;

import com.heartsignal.dev.domain.nosql.MeetChat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetChatRepository extends MongoRepository<MeetChat, String> {
}
