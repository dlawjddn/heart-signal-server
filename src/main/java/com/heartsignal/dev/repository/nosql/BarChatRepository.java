package com.heartsignal.dev.repository.nosql;

import com.heartsignal.dev.domain.nosql.BarChat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarChatRepository extends MongoRepository<BarChat, String> {

}