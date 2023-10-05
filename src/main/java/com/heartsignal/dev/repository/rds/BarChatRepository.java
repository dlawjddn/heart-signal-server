package com.heartsignal.dev.repository.rds;

import com.heartsignal.dev.domain.rds.BarChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarChatRepository extends JpaRepository<BarChat, String> {
}
