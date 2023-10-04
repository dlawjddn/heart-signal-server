package com.heartsignal.dev.repository;

import com.heartsignal.dev.domain.BarChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarChatRepository extends JpaRepository<BarChat, String> {
}
