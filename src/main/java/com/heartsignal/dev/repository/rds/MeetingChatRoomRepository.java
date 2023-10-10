package com.heartsignal.dev.repository.rds;

import com.heartsignal.dev.domain.rds.MeetingChatRoom;
import com.heartsignal.dev.domain.rds.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MeetingChatRoomRepository extends JpaRepository<MeetingChatRoom, Long> {

    @Query("SELECT m FROM MeetingChatRoom m WHERE m.team1 = :team OR m.team2 = :team")
    Optional<MeetingChatRoom> findByTeam1OrTeam2(@Param("team") Team team);
    boolean existsById(Long id);
}
