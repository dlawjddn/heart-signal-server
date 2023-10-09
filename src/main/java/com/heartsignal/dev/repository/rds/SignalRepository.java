package com.heartsignal.dev.repository.rds;

import com.heartsignal.dev.domain.rds.Signal;
import com.heartsignal.dev.domain.rds.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SignalRepository extends JpaRepository<Signal, Long> {
    List<Signal> findBySender(Team sendTeam);
    List<Signal> findByReceiver(Team receivedTeam);
    Optional<Signal> findBySenderAndReceiver(Team sender, Team receiver);
    boolean existsBySenderAndReceiver(Team myTeam, Team otherTeam);
}
