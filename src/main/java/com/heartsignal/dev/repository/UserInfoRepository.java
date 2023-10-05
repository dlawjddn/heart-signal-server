package com.heartsignal.dev.repository;

import com.heartsignal.dev.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    boolean existsByNickname(String nickname);
    Optional<UserInfo> findByNickname(String nickname);
}
