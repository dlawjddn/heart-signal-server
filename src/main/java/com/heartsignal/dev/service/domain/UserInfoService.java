package com.heartsignal.dev.service.domain;

import com.heartsignal.dev.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;
    public boolean isExistedNickname(String nickname){
        return userInfoRepository.existsByNickname(nickname);
    }
}
