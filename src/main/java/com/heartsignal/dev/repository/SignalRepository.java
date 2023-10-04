package com.heartsignal.dev.repository;

import com.heartsignal.dev.domain.Signal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignalRepository extends JpaRepository<Signal, Long> {
}
