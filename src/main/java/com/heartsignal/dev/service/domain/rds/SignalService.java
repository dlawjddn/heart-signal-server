package com.heartsignal.dev.service.domain.rds;

import com.heartsignal.dev.domain.rds.Signal;
import com.heartsignal.dev.domain.rds.Team;
import com.heartsignal.dev.exception.custom.CustomException;
import com.heartsignal.dev.exception.custom.ErrorCode;
import com.heartsignal.dev.repository.rds.SignalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignalService {
    private final SignalRepository signalRepository;
    @Transactional
    public void saveSignal(Team sendTeam, Team receivedTeam){
        signalRepository.save(
                Signal.builder()
                        .sender(sendTeam)
                        .receiver(receivedTeam)
                        .build());
        log.info("시그널 저장 성공");
    }
    public List<Signal> findSendingSignal(Team myTeam){
        return signalRepository.findBySender(myTeam);
    }
    public List<Signal> findReceivedSignal(Team myTeam){
        return signalRepository.findByReceiver(myTeam);
    }
    public Signal findBySenderAndReceiver(Team sender, Team receiver){
        return signalRepository.findBySenderAndReceiver(sender, receiver)
                .orElseThrow(() -> new CustomException(ErrorCode.SIGNAL_NOT_FOUND));
    }
    public boolean checkCantSend(Team sendTeam, Team receivedTeam){
        return signalRepository.existsBySenderAndReceiver(sendTeam, receivedTeam);
    }
    @Transactional
    public void deleteSignal(Signal signal){
        signalRepository.delete(signal);
        log.info("시그널 삭제 완료");
    }

    public boolean isMutualFollow(Team teamA, Team teamB) {
        boolean existsSignalFromAtoB = signalRepository.findBySenderAndReceiver(teamA, teamB).isPresent();
        boolean existsSignalFromBtoA = signalRepository.findBySenderAndReceiver(teamB, teamA).isPresent();

        return existsSignalFromAtoB && existsSignalFromBtoA;
    }

    public boolean checkCantSend(Team sendTeam, Team receivedTeam){
        return signalRepository.existsBySenderAndReceiver(sendTeam, receivedTeam);
    }
}
