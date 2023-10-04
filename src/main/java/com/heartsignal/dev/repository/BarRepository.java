package com.heartsignal.dev.repository;

import com.heartsignal.dev.domain.Bar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarRepository extends JpaRepository<Bar, Long> {
    @Query("SELECT b.location from Bar b")
    List<String> findLocation();
    List<Bar> findByLocation(String location);
}
