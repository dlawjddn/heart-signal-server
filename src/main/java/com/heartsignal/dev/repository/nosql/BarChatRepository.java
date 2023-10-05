package com.heartsignal.dev.repository.nosql;

import com.heartsignal.dev.domain.nosql.BarChat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BarChatRepository extends MongoRepository<BarChat, String> {

}