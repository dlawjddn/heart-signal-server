package com.heartsignal.dev.repository.rds;

import com.heartsignal.dev.domain.rds.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    boolean existsByNickname(String nickname);
    Optional<UserInfo> findByGenderAndNickname(String gender, String nickname);
    Optional<UserInfo> findByNickname(String nickname);
}
