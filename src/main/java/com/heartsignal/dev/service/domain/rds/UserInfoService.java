package com.heartsignal.dev.service.domain.rds;

import com.heartsignal.dev.domain.rds.User;
import com.heartsignal.dev.domain.rds.UserInfo;
import com.heartsignal.dev.dto.userInfo.request.SaveAdditionalInfoDTO;
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
    public void saveAdditionalInfo(User user, SaveAdditionalInfoDTO additionalInfo){
        log.info("userId ={}", user.getId().toString());

        UserInfo userInfo = userInfoRepository.save(UserInfo.builder()
                .gender((additionalInfo.getGender()))
                .nickname(additionalInfo.getNickname())
                .mbti(additionalInfo.getMbti())
                .lookAlike(additionalInfo.getFace())
                .selfInfo(additionalInfo.getSelfInfo())
                .user(user)
                .build());
        user.updateUserInfo(userInfo);
        /**
         * 양방향 연관관계설정
         */
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
    public UserInfo findByGenderAndNickname(String gender, String nickname){
        return userInfoRepository.findByGenderAndNickname(gender, nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.USERINFO_NOT_FOUND));
    }
    public UserInfo findByNickName(String nickname){
        return userInfoRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.NICKNAME_NOT_FOUND));
    }
}
