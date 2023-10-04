
package com.heartsignal.dev.handler;


import com.heartsignal.dev.domain.Role;
import com.heartsignal.dev.domain.User;
import com.heartsignal.dev.oauth.CustomOAuth2user;
import com.heartsignal.dev.oauth.PrincipalDetails;
import com.heartsignal.dev.repository.UserRepository;
import com.heartsignal.dev.service.domain.UserService;
import com.heartsignal.dev.service.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UserService userService;
    private final UserRepository userRepository;
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

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
        String bearerAccessToken = "Bearer " + accessToken;
        String bearerRefreshToken = "Bearer " + refreshToken;
        /**
         * TODO url 문제 해결 필요 + Bearer + 문제 해결 필요
         */
        String cookieValueForAccess = URLEncoder.encode(bearerAccessToken, StandardCharsets.UTF_8);
        String cookieValueForRefresh = URLEncoder.encode(bearerRefreshToken, StandardCharsets.UTF_8);

        jwtService.updateRefreshToken(user, refreshToken);

        Cookie accessCookie = new Cookie("accessCookie", cookieValueForAccess);
        Cookie refreshCookie = new Cookie("refreshCookie", cookieValueForRefresh);

        /***
         * TODO
         * 나중에 바꿔야하는값
         */
        accessCookie.setDomain("localhost");  // 도메인을 localhost로 설정
        refreshCookie.setDomain("localhost"); // 도메인을 localhost로 설정

        if (user.getRole() == Role.GUEST) {
            log.info("처음 가입하는 사용자 입니다");
            /**
             * TODO
             * 나중에 바꿔야하는 값
             * 리프레시는 시큐어쿠키로 바꿀것
             */
            accessCookie.setPath("/user-info");
            refreshCookie.setPath("/user-info");
            response.addCookie(accessCookie);
            response.addCookie(refreshCookie);

            response.sendRedirect("http://localhost:3000/user-info"); // localhost:3000으로 리다이렉트
            return;
        }

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
        response.sendRedirect("http://localhost:3000/home"); // localhost:3000으로 리다이렉트
    }
}
