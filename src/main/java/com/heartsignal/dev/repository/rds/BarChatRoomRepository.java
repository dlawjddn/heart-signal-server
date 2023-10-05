package com.heartsignal.dev.repository.rds;

import com.heartsignal.dev.domain.rds.BarChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarChatRoomRepository extends JpaRepository<BarChatRoom, String> {
}
