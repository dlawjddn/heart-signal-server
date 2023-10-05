package com.heartsignal.dev.Facade;

import com.heartsignal.dev.domain.rds.Team;
import com.heartsignal.dev.domain.rds.User;
import com.heartsignal.dev.domain.rds.UserInfo;
import com.heartsignal.dev.dto.signal.response.SignalDTO;
import com.heartsignal.dev.dto.team.response.SignalTeamsInfo;
import com.heartsignal.dev.dto.team.request.SaveTeamInfo;
import com.heartsignal.dev.dto.team.response.TeamDTO;
import com.heartsignal.dev.dto.team.response.TeamDetailsDTO;
import com.heartsignal.dev.dto.userInfo.response.AdditionalInfoDTO;
import com.heartsignal.dev.dto.userInfo.response.ExistedNickname;
import com.heartsignal.dev.dto.userInfo.request.SaveAdditionalInfo;
import com.heartsignal.dev.exception.custom.CustomException;
import com.heartsignal.dev.exception.custom.ErrorCode;
import com.heartsignal.dev.service.domain.rds.*;
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
    private final MeetingChatRoomService meetingChatRoomService;
    private final BarService barService;
    private final BarChatroomService barChatroomService;
    private final SignalService signalService;

    /**
     * 추가 정보 기입
     */
    public void saveAdditionalInfo(User user, SaveAdditionalInfo additionalInfo){
        User byId = userService.findById(user.getId());
        userInfoService.saveAdditionalInfo(byId, additionalInfo);
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
     * 시그널
     */

    // 시그널 리스트 제공
    public SignalTeamsInfo provideSignalList(User leader){
        List<TeamDTO> teamDTOs = teamService.findSignalList(leader).stream()
                .map(team -> new TeamDTO(team.getId(), team.getTitle()))
                .toList();
        return new SignalTeamsInfo(teamDTOs);
    }

    // 시그널 상세 정보 제공
    public TeamDetailsDTO provideTeamDetails(User user, Long teamId){
        Team findTeam = teamService.findById(teamId);
        List<AdditionalInfoDTO> memberInfos = findTeam.getMembers().stream()
                .map(User::getUserInfo).toList().stream()
                .map(userInfo -> new AdditionalInfoDTO(userInfo.getNickname(), userInfo.getMbti(), userInfo.getLookAlike(), userInfo.getSelfInfo()))
                .toList(); // 리더를 제외한 멤버들의 추가 정보 리스트
        UserInfo leaderInfo = findTeam.getLeader().getUserInfo();
        memberInfos.add(new AdditionalInfoDTO(leaderInfo.getNickname(), leaderInfo.getMbti(), leaderInfo.getLookAlike(), leaderInfo.getSelfInfo()));

        Team myTeam = user.getTeam();
        return new TeamDetailsDTO(myTeam.getTitle(), myTeam.getLeader().equals(user), memberInfos);
    }

    //시그널 보내기
    public void sendSignal(User user, Long teamId){
        Team myTeam = user.getTeam();
        if (!myTeam.getLeader().equals(user))
            throw new CustomException(ErrorCode.ONLY_LEADER); // 일반 유저라면 exception
        signalService.saveSignal(myTeam, teamService.findById(teamId));
    }

    // 시그널 거절하기
    public void rejectSignal(User user, Long teamId, boolean reject){
        Team myTeam = user.getTeam();
        Team otherTeam = teamService.findById(teamId);
        if (!myTeam.getLeader().equals(user))
            throw new CustomException(ErrorCode.ONLY_LEADER);
        if (reject)
            signalService.deleteSignal(signalService.findBySenderAndReceiver(otherTeam, myTeam));
        else
            signalService.deleteSignal(signalService.findBySenderAndReceiver(myTeam, otherTeam));
    }

    /**
     * 주점
     */

    // 주점 목록 조회


    /**
     * 매칭 확인하기
     */

    public SignalDTO checkMatching(User user){
        Team myTeam = user.getTeam();
        List<TeamDTO> sendingInfos = signalService.findSendingSignal(myTeam).stream()
                .map(signal -> new TeamDTO(signal.getReceiver().getId(), signal.getReceiver().getTitle()))
                .toList();
        List<TeamDTO> receivedInfos = signalService.findReceivedSignal(myTeam).stream()
                .map(signal -> new TeamDTO(signal.getSender().getId(), signal.getSender().getTitle()))
                .toList();
        return new SignalDTO(sendingInfos, receivedInfos);
    }
}
