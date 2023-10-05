package com.heartsignal.dev.service.domain.rds;

import com.heartsignal.dev.domain.rds.User;
import com.heartsignal.dev.domain.rds.UserInfo;
import com.heartsignal.dev.dto.userInfo.request.SaveAdditionalInfo;
import com.heartsignal.dev.exception.custom.CustomException;
import com.heartsignal.dev.exception.custom.ErrorCode;
import com.heartsignal.dev.repository.rds.UserInfoRepository;
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
        user.updateRoleToUser();
        /**
         * 유저 ROLE 변경 GUEST -> USER
         * 추가 정보기입 끝
         */
        log.info("회원 추가 정보 저장 완료");
    }
    public boolean isExistedNickname(String nickname){
        return userInfoRepository.existsByNickname(nickname);
    }
    public UserInfo findByNickName(String nickname){
        return userInfoRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.NICKNAME_NOT_FOUND));
    }
}
