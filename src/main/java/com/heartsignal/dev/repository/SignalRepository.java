package com.heartsignal.dev.repository;

import com.heartsignal.dev.domain.Signal;
import com.heartsignal.dev.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SignalRepository extends JpaRepository<Signal, Long> {
    List<Signal> findBySender(Team sendTeam);
    List<Signal> findByReceiver(Team receivedTeam);
}
