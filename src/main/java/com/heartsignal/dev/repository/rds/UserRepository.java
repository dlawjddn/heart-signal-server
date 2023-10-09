package com.heartsignal.dev.repository.rds;

import com.heartsignal.dev.domain.rds.Role;
import com.heartsignal.dev.domain.rds.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialId(String socialId);
    Optional<User> findBySocialIdAndRole(String socialId, Role role);

    Optional<User> findByRefreshToken(String refreshToken);
    Optional<User> findById(Long id);
}
