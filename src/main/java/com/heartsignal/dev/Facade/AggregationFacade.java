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
import com.heartsignal.dev.dto.report.response.CanReportDTO;
import com.heartsignal.dev.dto.signal.response.SignalDTO;
import com.heartsignal.dev.dto.team.request.SaveTeamDTO;
import com.heartsignal.dev.dto.team.response.SignalTeamsDTO;
import com.heartsignal.dev.dto.team.response.TeamDTO;
import com.heartsignal.dev.dto.team.response.TeamDetailsDTO;
import com.heartsignal.dev.dto.userInfo.request.SaveAdditionalInfoDTO;
import com.heartsignal.dev.dto.userInfo.response.*;
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
     *  회원 신고 확인하기
     */

    // 회원 리포트 카운트 확인하기
    private boolean checkUserReport(User user){
        return user.getReportCount() < 5;
    }
    /**
     * 메인페이지
     */

    // 메인 페이지
    public MainPageDTO showMainPage(User tempUser){
        User user = userService.findById(tempUser.getId());
        if (!checkUserReport(user))
            throw new CustomException(ErrorCode.BANNED);
        int checkPoint = 0;
        if (user.getTeam() == null) checkPoint = 1;
        else {
            if (user.getTeam().getStatus() == 1) // 매칭이 된 상태
                checkPoint = 3;
            else checkPoint = 2; // 팀은 결성했지만 매칭은 안 된 상태
        }
        return MainPageDTO.builder()
                .matchStatus(checkPoint)
                .build();
    }

    /**
     * 추가 정보 기입
     */

    // 추가 정보 저장
    public void saveAdditionalInfo(User tempUser, SaveAdditionalInfoDTO additionalInfo){
        User user = userService.findById(tempUser.getId());
        if (!checkUserReport(user))
            throw new CustomException(ErrorCode.BANNED);
        userInfoService.saveAdditionalInfo(user, additionalInfo);
    }

    // 닉네임 중복확인
    public ExistedNicknameDTO checkDuplicatedNickname(String nickname){
        return ExistedNicknameDTO.builder()
                .isExisted(userInfoService.isExistedNickname(nickname))
                .build();
    }
    /**
     * 마이페이지
     */
    // 마이페이지
    public MyPageDTO showMyPage(User tempUser) {
        User user = userService.findById(tempUser.getId());
        if (!checkUserReport(user))
            throw new CustomException(ErrorCode.BANNED);
        UserInfo myAddiInfo = user.getUserInfo();
        int checkPoint = 0;
        if (user.getTeam() == null) checkPoint = 1;
        else {
            if (user.getTeam().getStatus() == 1) // 매칭이 된 상태
                checkPoint = 3;
            else checkPoint = 2; // 팀은 결성했지만 매칭은 안 된 상태
        }
        return MyPageDTO.builder()
                .nickname(myAddiInfo.getNickname())
                .mbti(myAddiInfo.getMbti())
                .face(myAddiInfo.getLookAlike())
                .selfInfo(myAddiInfo.getSelfInfo())
                .matchStatus(checkPoint)
                .build();
    }

    // 팀 삭제하기
    public void deleteTeam(User tempUser){
        User user = userService.findById(tempUser.getId());
        if (!checkUserReport(user))
            throw new CustomException(ErrorCode.BANNED);
        Team myTeam = user.getTeam();
        if (!myTeam.getLeader().equals(user))
            throw new CustomException(ErrorCode.ONLY_LEADER); // 리더가 아닌 사람이 팀을 삭제하려는 경우
        teamService.deleteTeam(myTeam);
    }

    // 신고하기

    public void reportUser(String nickname){
        User reportedUser = userService.findById(userInfoService.findByNickName(nickname).getId());
        userService.reportUser(reportedUser);
    }

    public CanReportDTO checkCanReport(String nickname){
        return CanReportDTO.builder()
                .canReport(userInfoService.isExistedNickname(nickname))
                .build();
    }
    /**
     * 그룹화
     */

    // 그룹이 되기 위한 사용자의 성별과 팀 존재 유무 파악
    public CanGroupDTO canBeGroupMember(User tempUser, String nickname){
        User user = userService.findById(tempUser.getId()); // 리더
        if (!checkUserReport(user))
            throw new CustomException(ErrorCode.BANNED);
        // 로그인 한 사람(리더의 성별과 같고, 닉네임이 존재 해야함)
        UserInfo otherUserInfo = userInfoService.findByNickName(nickname);
        boolean canGroup = false; // dto 에 담을 값
        if (otherUserInfo.getGender().equals(user.getUserInfo().getGender()) && user.getTeam() == null && otherUserInfo.getUser().getTeam() == null && !user.getUserInfo().getNickname().equals(otherUserInfo.getNickname()))
            canGroup = true;

        return CanGroupDTO.builder()
                .canGroup(canGroup)
                .build();
    }

    // 그룹 생성
    public void makeTeam(User tempLeader, SaveTeamDTO teamInfo){
        User user = userService.findById(tempLeader.getId());
        if (!checkUserReport(user))
            throw new CustomException(ErrorCode.BANNED);
        List<User> members = new ArrayList<>(teamInfo.getNicknames().stream()
                .map(userInfoService::findByNickName).toList().stream()
                .map(userInfo -> userService.findById(userInfo.getId())).toList());
        members.add(tempLeader);

        log.info("팀 구성원 추출 완료");
        teamService.saveTeam(user, members, teamInfo.getTitle());
    }
    /**
     * 시그널
     */

    // 시그널 리스트 제공
    public SignalTeamsDTO provideSignalList(User tempLeader){
        User leader = userService.findById(tempLeader.getId());
        if (!checkUserReport(leader))
            throw new CustomException(ErrorCode.BANNED);
        Team myTeam = leader.getTeam();
        List<TeamDTO> teamDTOs = teamService.findSignalList(leader).stream()
                .filter(team -> !signalService.checkCantSend(myTeam, team)) // team: 내가 보낼 수 있는 team 을 의미함
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
        if (!checkUserReport(user))
            throw new CustomException(ErrorCode.BANNED);
        Team findTeam = teamService.findById(teamId);
        List<AdditionalInfoDTO> memberInfos = new ArrayList<>(findTeam.getMembers().stream()
                .map(User::getUserInfo).toList().stream()
                .map(userInfo -> AdditionalInfoDTO.builder()
                        .nickname(userInfo.getNickname())
                        .mbti(userInfo.getMbti())
                        .face(userInfo.getLookAlike())
                        .selfInfo(userInfo.getSelfInfo())
                        .build()).toList()); // 리더를 제외한 멤버들의 추가 정보 리스트

        Team myTeam = user.getTeam();
        return TeamDetailsDTO.builder()
                .title(findTeam.getTitle())
                .isLeader(myTeam.getLeader().equals(user))
                .usersInfo(memberInfos)
                .build();
    }

    //시그널 보내기
    public void sendSignal(User tempUser, Long teamId){
        User user = userService.findById(tempUser.getId());
        if (!checkUserReport(user))
            throw new CustomException(ErrorCode.BANNED);
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
         * 4. 동겸이가 할일 시그널 다지우기
         */
        if (signalService.isMutualFollow(myTeam, otherTeam)) {
            Long meetingChatRoomId = meetingChatRoomService.makeMeetingChatRoom(myTeam, otherTeam);
            chatService.saveMeetingChat(meetingChatRoomId.toString());
        }
    }

    // 시그널 거절하기
    public void rejectSignal(User tempUser, Long teamId, boolean reject){
        User user = userService.findById(tempUser.getId());
        if (!checkUserReport(user))
            throw new CustomException(ErrorCode.BANNED);
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
            int cnt = 0;
            for (int i=0; i<barInfoDTOS.size(); i++){
                if (i % 2 == 0){
                    barContentDTOS.add(BarContentDTO.builder()
                            .first(barInfoDTOS.get(i))
                            .second(BarInfoDTO.builder().barID(0L).name("").groupName("").build())
                            .build());
                }
                else{
                    barContentDTOS.get(cnt).setSecond(barInfoDTOS.get(i));
                    cnt++;
                }
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
        if (!checkUserReport(user))
            throw new CustomException(ErrorCode.BANNED);
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
                        .date(parsedDate.toInstant())
                        .build()
        );
        chatService.saveBarChat(chat);
    }


    /**
     * 주점
     * 채팅 내역 불러오기
     */
    public MessageListDTO provideBarChatInfos(String chatId, OffsetDateTime dateTime) {
        Chat chat = chatService.findChatById(chatId);
        if(chat.getMessages() == null){
            return MessageListDTO.builder()
                    .messageList(null)
                    .build();
        }

        List<Message> sortedMessages = sortMessagesByDate(chat).stream()
                .filter(msg -> msg.getDate().isAfter(dateTime.toInstant()))
                .collect(Collectors.toList());

        return MessageListDTO.builder()
                .messageList(convertToMessageDTOList(sortedMessages))
                .build();
    }

    /**
     * 미팅
     * 채팅 내역 불러오기
     */
    public MessageListDTO provideMeetingChatInfos(User tempUser) {
        User user = userService.findById(tempUser.getId());
        if (!checkUserReport(user))
            throw new CustomException(ErrorCode.BANNED);
        Team team = user.getTeam();
        Long meetingChatRoomId = meetingChatRoomService.findMeetingChatRoomByTeam(team);
        Chat chat = chatService.findChatById(meetingChatRoomId.toString());
        if(chat.getMessages() == null){
            return MessageListDTO.builder()
                    .messageList(null)
                    .build();
        }
        return MessageListDTO.builder()
                .messageList(convertToMessageDTOList(sortMessagesByDate(chat)))
                .build();
    }


    private List<MessageDTO> convertToMessageDTOList(List<Message> messages) {
        return messages.stream()
                .map(msg -> MessageDTO.builder()
                        .content(msg.getContent())
                        .sender(msg.getSender())
                        .sendTime(msg.getDate().toString())
                        .build())
                .collect(Collectors.toList());
    }

    private List<Message> sortMessagesByDate(Chat chat) {
        if(chat.getMessages() == null){
            return null;
        }
        return chat.getMessages()
                .stream()
                .sorted(Comparator.comparing(Message::getDate))
                .collect(Collectors.toList());
    }

    public void deleteMeetingRoom(Long roomId) {
        meetingChatRoomService.deleteMeetingRoom(roomId);
        chatService.deleteChat(roomId);
    }
}

