package com.heartsignal.dev.service.domain;


import com.heartsignal.dev.domain.User;
import com.heartsignal.dev.repository.UserRepository;
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
}
