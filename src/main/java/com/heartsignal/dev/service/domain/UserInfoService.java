package com.heartsignal.dev.service.domain;

import com.heartsignal.dev.domain.User;
import com.heartsignal.dev.domain.UserInfo;
import com.heartsignal.dev.dto.userInfo.SaveAdditionalInfo;
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
    @Transactional
    public void saveAdditionalInfo(User user, SaveAdditionalInfo additionalInfo){
        userInfoRepository.save(new UserInfo(user.getId(),
                additionalInfo.getGender(),
                additionalInfo.getNickname(),
                additionalInfo.getMbti(),
                additionalInfo.getFace(),
                additionalInfo.getSelfInfo(),
                user));
    }
    public boolean isExistedNickname(String nickname){
        return userInfoRepository.existsByNickname(nickname);
    }
}
