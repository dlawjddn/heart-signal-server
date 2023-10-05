package com.heartsignal.dev.repository.rds;

import com.heartsignal.dev.domain.rds.MeetingChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingChatRoomRepository extends JpaRepository<MeetingChatRoom, Long> {
}
