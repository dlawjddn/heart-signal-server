
package com.heartsignal.dev.handler;


import com.heartsignal.dev.domain.rds.Role;
import com.heartsignal.dev.domain.rds.User;
import com.heartsignal.dev.oauth.CustomOAuth2user;
import com.heartsignal.dev.oauth.PrincipalDetails;
import com.heartsignal.dev.repository.rds.UserRepository;
import com.heartsignal.dev.service.domain.rds.UserService;
import com.heartsignal.dev.service.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Value("${redirect.url.userInfo}")
    private String userInfoUrl;

    @Value("${redirect.url.main}")
    private String mainPageUrl;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 로그인 성공!! 토큰 세팅에 들어갑니다");

        CustomOAuth2user customOAuth2user = (CustomOAuth2user) authentication.getPrincipal();
        String socialId = customOAuth2user.getId();
        User user = userRepository.findBySocialId(socialId).orElseThrow(() -> new UsernameNotFoundException("유저를 찾지 못했습니다"));
        PrincipalDetails principalDetails = new PrincipalDetails(user);


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(principalDetails, null,
                        authoritiesMapper.mapAuthorities(principalDetails.getAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        String accessToken = jwtService.createAccessToken(user.getSocialId());
        String refreshToken = jwtService.createRefreshToken();
        String bearerAccessToken = "Bearer" + accessToken;


        jwtService.updateRefreshToken(user, refreshToken);

        Cookie accessCookie = new Cookie("accessCookie", bearerAccessToken);


        /***
         * TODO
         * 나중에 바꿔야하는값
         */
        accessCookie.setDomain("localhost");  // 도메인을 localhost로 설정

        if (user.getRole() == Role.GUEST) {
            log.info("처음 가입하는 사용자 입니다");
            /**
             * TODO
             * 나중에 바꿔야하는 값
             * 리프레시는 시큐어쿠키로 바꿀것
             * 리프레시 쿠키 안넘기는 걸로
             */
            accessCookie.setPath("/");
            response.addCookie(accessCookie);

            response.sendRedirect(userInfoUrl);
            return;
        }

        response.addCookie(accessCookie);
        response.sendRedirect(mainPageUrl);
    }
}
