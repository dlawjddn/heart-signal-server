package com.heartsignal.dev.Facade;

import com.heartsignal.dev.domain.nosql.Chat;
import com.heartsignal.dev.domain.nosql.Message;
import com.heartsignal.dev.domain.rds.Team;
import com.heartsignal.dev.domain.rds.User;
import com.heartsignal.dev.domain.rds.UserInfo;
import com.heartsignal.dev.dto.bar.response.BarContentDTO;
import com.heartsignal.dev.dto.bar.response.BarInfoDTO;
import com.heartsignal.dev.dto.bar.response.BarListDTO;
import com.heartsignal.dev.dto.chat.response.MessageDTO;
import com.heartsignal.dev.dto.chat.response.MessageListDTO;
import com.heartsignal.dev.dto.signal.response.SignalDTO;
import com.heartsignal.dev.dto.team.request.SaveTeamDTO;
import com.heartsignal.dev.dto.team.response.SignalTeamsDTO;
import com.heartsignal.dev.dto.team.response.TeamDTO;
import com.heartsignal.dev.dto.team.response.TeamDetailsDTO;
import com.heartsignal.dev.dto.userInfo.request.SaveAdditionalInfoDTO;
import com.heartsignal.dev.dto.userInfo.response.AdditionalInfoDTO;
import com.heartsignal.dev.dto.userInfo.response.CanGroupDTO;
import com.heartsignal.dev.dto.userInfo.response.ExistedNicknameDTO;
import com.heartsignal.dev.exception.custom.CustomException;
import com.heartsignal.dev.exception.custom.ErrorCode;
import com.heartsignal.dev.service.domain.nosql.ChatService;
import com.heartsignal.dev.service.domain.rds.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AggregationFacade {
    private final UserService userService;
    private final UserInfoService userInfoService;
    private final TeamService teamService;
    private final BarService barService;
    private final SignalService signalService;
    private final MeetingChatRoomService meetingChatRoomService;
    private final BarChatroomService barChatroomService;
    private final ChatService chatService;

    /**
     * 추가 정보 기입
     */
    public void saveAdditionalInfo(User tempUser, SaveAdditionalInfoDTO additionalInfo){
        userInfoService.saveAdditionalInfo(userService.findById(tempUser.getId()), additionalInfo);
    }

    public ExistedNicknameDTO checkDuplicatedNickname(String nickname){
        return ExistedNicknameDTO.builder()
                .isExisted(userInfoService.isExistedNickname(nickname))
                .build();
    }
    /**
     * 마이페이지
     */
    public AdditionalInfoDTO showMyPage(User tempUser) {
        User user = userService.findById(tempUser.getId());
        UserInfo myAddiInfo = user.getUserInfo();
        return AdditionalInfoDTO.builder()
                .nickname(myAddiInfo.getNickname())
                .mbti(myAddiInfo.getMbti())
                .face(myAddiInfo.getLookAlike())
                .selfInfo(myAddiInfo.getSelfInfo())
                .build();
    }
    /**
     * 그룹화
     */

    // 그룹이 되기 위한 사용자의 성별과 팀 존재 유무 파악
    public CanGroupDTO canBeGroupMember(User tempUser, String nickname){
        User user = userService.findById(tempUser.getId()); // 리더
        // 로그인 한 사람(리더의 성별과 같고, 닉네임이 존재 해야함)
        UserInfo otherUserInfo = userInfoService.findByGenderAndNickname(user.getUserInfo().getGender(), nickname);
        User otherUser = userService.findById(otherUserInfo.getId()); // 멤버가 되려는 유저

        // otherUser 에 대한 조건 처리 -> 결과값 도출
        boolean canGroup = (user.getUserInfo().getGender().equals(otherUserInfo.getGender()) && otherUser.getTeam() == null);
        return CanGroupDTO.builder()
                .canGroup(canGroup)
                .build();
    }

    // 그룹 생성
    public void makeTeam(User tempLeader, SaveTeamDTO teamInfo){
        List<User> members = teamInfo.getNicknames().stream()
                .map(userInfoService::findByNickName).toList().stream()
                .map(userInfo -> userService.findById(userInfo.getId())).toList();
        log.info("팀 구성원 추출 완료");
        teamService.saveTeam(userService.findById(tempLeader.getId()), members, teamInfo.getTitle());
    }
    /**
     * 시그널
     */

    // 시그널 리스트 제공
    public SignalTeamsDTO provideSignalList(User tempLeader){
        User leader = userService.findById(tempLeader.getId());
        List<TeamDTO> teamDTOs = teamService.findSignalList(leader).stream()
                .map(team -> TeamDTO.builder()
                        .teamId(team.getId())
                        .teamName(team.getTitle())
                        .build())
                .toList();
        return SignalTeamsDTO.builder()
                .teams(teamDTOs)
                .build();
    }

    // 시그널 상세 정보 제공
    public TeamDetailsDTO provideTeamDetails(User tempUser, Long teamId){
        User user = userService.findById(tempUser.getId());
        Team findTeam = teamService.findById(teamId);
        List<AdditionalInfoDTO> memberInfos = findTeam.getMembers().stream()
                .map(User::getUserInfo).toList().stream()
                .map(userInfo ->  AdditionalInfoDTO.builder()
                        .nickname(userInfo.getNickname())
                        .mbti(userInfo.getMbti())
                        .face(userInfo.getLookAlike())
                        .selfInfo(userInfo.getSelfInfo())
                        .build()).toList(); // 리더를 제외한 멤버들의 추가 정보 리스트

        UserInfo leaderInfo = findTeam.getLeader().getUserInfo();
        memberInfos.add(AdditionalInfoDTO.builder()
                .nickname(leaderInfo.getNickname())
                .mbti(leaderInfo.getMbti())
                .face(leaderInfo.getLookAlike())
                .selfInfo(leaderInfo.getSelfInfo())
                .build());

        Team myTeam = user.getTeam();
        return TeamDetailsDTO.builder()
                .title(myTeam.getTitle())
                .isLeader(myTeam.getLeader().equals(user))
                .usersInfo(memberInfos)
                .build();
    }

    //시그널 보내기
    public void sendSignal(User tempUser, Long teamId){
        User user = userService.findById(tempUser.getId());
        Team myTeam = user.getTeam();
        if (!myTeam.getLeader().equals(user))
            throw new CustomException(ErrorCode.ONLY_LEADER); // 일반 유저라면 exception
        Team otherTeam = teamService.findById(teamId);
        signalService.saveSignal(myTeam, otherTeam);
        /**
         * 맞시그널인지 확인하는 메소드
         * 만약 true면
         * 1. myTeam, OtherTeam의 status를 true로 업데이트 하기
         * 2. 채팅방 반들기
         * 3.   myTeam.updateTeamStatus(true);
         *             otherTeam.updateTeamStatus(true);
         *             2개 뺴기
         * 4. 동겸이가 할일 시그널 다지우기
         */
        if (signalService.isMutualFollow(myTeam, otherTeam)) {
            myTeam.updateTeamStatus(true);
            otherTeam.updateTeamStatus(true);
            Long meetingChatRoomId = meetingChatRoomService.makeMeetingChatRoom(myTeam, otherTeam);
            chatService.saveChat(meetingChatRoomId);
        }
    }

    // 시그널 거절하기
    public void rejectSignal(User tempUser, Long teamId, boolean reject){
        User user = userService.findById(tempUser.getId());
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

    // 주점 목록 조회 -> 야호 해냈당
    public List<BarListDTO> provideBarInfos(){
        List<BarListDTO> barListDTOS = new ArrayList<>();
        List<String> locations = barService.findLocations(); // -> 위치에 해당하는 문자열 배열
        for (String location : locations) {
            List<BarContentDTO> barContentDTOS = new ArrayList<>();
            List<BarInfoDTO> barInfoDTOS = barService.findBarsInLocation(location).stream()
                    .map(bar -> BarInfoDTO.builder()
                            .barID(bar.getId())
                            .groupName(bar.getGroup())
                            .name(bar.getName())
                            .build())
                    .toList();
            for (int i=0; i<barInfoDTOS.size(); i+=2){
                barContentDTOS.add(BarContentDTO.builder()
                                .first(barInfoDTOS.get(i))
                                .second(barInfoDTOS.get(i+1))
                        .build());
            }
            barListDTOS.add(BarListDTO.builder()
                    .name(location)
                    .pubs(barContentDTOS)
                    .build());
        }
        return barListDTOS;
    }


    /**
     * 매칭 확인하기
     */

    public SignalDTO checkMatching(User tempUser){
        User user = userService.findById(tempUser.getId());
        Team myTeam = user.getTeam();
        List<TeamDTO> sendingInfos = signalService.findSendingSignal(myTeam).stream()
                .map(signal -> TeamDTO.builder()
                        .teamId((signal.getReceiver().getId()))
                        .teamName(signal.getReceiver().getTitle())
                        .build())
                .toList();
        List<TeamDTO> receivedInfos = signalService.findReceivedSignal(myTeam).stream()
                .map(signal -> TeamDTO.builder()
                        .teamId(signal.getSender().getId())
                        .teamName(signal.getSender().getTitle())
                        .build())
                .toList();
        return SignalDTO.builder()
                .sendingSignal(sendingInfos)
                .receivedSignal(receivedInfos)
                .build();
    }

    /**
     * 채팅 저장하기
     */
    public void saveChat(MessageDTO messageDTO, String barId) {
        Chat chat = chatService.findChatById(barId);
        OffsetDateTime parsedDate = OffsetDateTime.parse(messageDTO.getSendTime());
        chat.getMessages().add(
                Message.builder()
                        .sender(messageDTO.getSender())
                        .content(messageDTO.getContent())
                        .date(parsedDate)
                        .build()
        );
    }


    /**
     * 주점
     * 채팅 내역 불러오기
     */
    public MessageListDTO provideChatInfos(String chatId, OffsetDateTime dateTime) {
        Chat chat = chatService.findChatById(chatId);

        List<Message> sortedMessages = chat.getMessages()
                .stream()
                .filter(msg -> msg.getDate().isAfter(dateTime))
                .sorted(Comparator.comparing(Message::getDate))
                .toList();

        List<MessageDTO> messageDTOList = sortedMessages.stream()
                .map(msg -> MessageDTO.builder()
                        .content(msg.getContent())
                        .sender(msg.getSender())
                        .sendTime(msg.getDate().toString())
                        .build()
                )
                .collect(Collectors.toList());

        return MessageListDTO.builder()
                .messageList(messageDTOList)
                .build();
    }
}

