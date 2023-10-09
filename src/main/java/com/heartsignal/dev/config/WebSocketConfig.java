package com.heartsignal.dev.config;

import com.heartsignal.dev.interceptor.HandShakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/subscribe");  //메세지 브로커가 메시지를 뿌릴때
        registry.setApplicationDestinationPrefixes("/app"); //클라에서 메세지 브로커한테 메시지를 보낼때
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-connection")
//                .addInterceptors(new HandShakeInterceptor())
                .setAllowedOrigins(
                        "ws://localhost:5173",
                        "http://localhost:3000",
                        "http://localhost:5173",
                        "http://43.202.145.101:8080",
                        "https://heart.dcs-hyungjoon.com",
                        "wss://heart.dcs-hyungjoon.com")
                .withSockJS();
    }
}