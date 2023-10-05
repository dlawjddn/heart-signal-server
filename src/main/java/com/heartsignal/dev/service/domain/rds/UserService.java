package com.heartsignal.dev.service.domain.rds;


import com.heartsignal.dev.domain.rds.User;
import com.heartsignal.dev.exception.custom.CustomException;
import com.heartsignal.dev.exception.custom.ErrorCode;
import com.heartsignal.dev.repository.rds.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void updateUserRoleToUser(User user) {
        user.updateRoleToUser();
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

}
