package com.heartsignal.dev.repository.rds;

import com.heartsignal.dev.domain.rds.MeetingChatRoom;
import com.heartsignal.dev.domain.rds.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * TODO
 * String -> Long
 */
@Repository
public interface MeetingChatRoomRepository extends JpaRepository<MeetingChatRoom, Long> {

    Optional<Long> findByTeam1OrTeam2(Team team);
}
