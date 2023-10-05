package com.heartsignal.dev.repository;

import com.heartsignal.dev.domain.nosql.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

}
