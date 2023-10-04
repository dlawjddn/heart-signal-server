package com.heartsignal.dev.repository;

import com.heartsignal.dev.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    boolean existsByNickname(String nickname);
}
