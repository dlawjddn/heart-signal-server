package com.heartsignal.dev.service.oauth;


import com.heartsignal.dev.domain.Role;
import com.heartsignal.dev.domain.User;
import com.heartsignal.dev.oauth.CustomOAuth2user;
import com.heartsignal.dev.oauth.KakaoUserInfo;
import com.heartsignal.dev.repository.UserRepository;
import com.heartsignal.dev.service.domain.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(attributes);

        User user = getUser(kakaoUserInfo);

        return new CustomOAuth2user(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())),
                attributes,
                userNameAttributeName,
                kakaoUserInfo.getId(),
                user.getRole()
                );
    }

    private User getUser(KakaoUserInfo kakaoUserInfo) {

        Optional<User> bySocialId = userRepository.findBySocialId(kakaoUserInfo.getId());
        if(bySocialId.isPresent()){
            return bySocialId.get();
        }


        User user = User.builder()
                .chatStatus(false)
                .reportCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .socialId(kakaoUserInfo.getId())
                .role(Role.GUEST)
                .build();

        userService.save(user);
        return user;
    }
}
