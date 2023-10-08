package com.heartsignal.dev.repository.rds;

import com.heartsignal.dev.domain.rds.Bar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarRepository extends JpaRepository<Bar, Long> {
    @Query("SELECT DISTINCT b.location FROM Bar b")
    List<String> findLocation();
    List<Bar> findByLocation(String location);
}
