package com.heartsignal.dev.Facade;

import com.heartsignal.dev.domain.User;
import com.heartsignal.dev.domain.UserInfo;
import com.heartsignal.dev.dto.team.response.SignalTeamsInfo;
import com.heartsignal.dev.dto.team.request.SaveTeamInfo;
import com.heartsignal.dev.dto.team.response.TeamDTO;
import com.heartsignal.dev.dto.userInfo.response.AdditionalInfoDTO;
import com.heartsignal.dev.dto.userInfo.response.ExistedNickname;
import com.heartsignal.dev.dto.userInfo.request.SaveAdditionalInfo;
import com.heartsignal.dev.service.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AggregationFacade {
    private final UserInfoService userInfoService;
    private final UserService userService;
    private final TeamService teamService;
    private final MeetingRoomService meetingRoomService;
    private final BarService barService;
    private final BarChatroomService barChatroomService;

    /**
     * 추가 정보 기입
     */
    public void saveAdditionalInfo(User user, SaveAdditionalInfo additionalInfo){
        userInfoService.saveAdditionalInfo(user, additionalInfo);
    }

    public ExistedNickname checkDuplicatedNickname(String nickname){
        return new ExistedNickname(userInfoService.isExistedNickname(nickname));
    }
    /**
     * 마이페이지
     */
    public AdditionalInfoDTO showMyPage(User user){
        UserInfo myAddiInfo = user.getUserInfo();
        return new AdditionalInfoDTO(myAddiInfo.getNickname(), myAddiInfo.getMbti(), myAddiInfo.getLookAlike(), myAddiInfo.getSelfInfo());
    }
    /**
     * 그룹화
     */
    public void makeTeam(User leader, SaveTeamInfo teamInfo){
        List<User> members = teamInfo.getNicknames().stream()
                .map(userInfoService::findByNickName).toList().stream()
                .map(userInfo -> userService.findById(userInfo.getId())).toList();
        log.info("팀 구성원 추출 완료");
        teamService.saveTeam(leader, members, teamInfo.getTitle());
    }
    /**
     * 시그널 리스트
     */
    public SignalTeamsInfo provideSignalList(User leader){
        List<TeamDTO> teamDTOs = teamService.findSignalList(leader).stream()
                .map(team -> new TeamDTO(team.getId(), team.getTitle()))
                .toList();
        return new SignalTeamsInfo(teamDTOs);
    }
}
