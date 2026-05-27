package com.example.config;

import com.example.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtService jwtService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Эндпоинт для подключения WebSocket (SockJS fallback не обязателен)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*"); // настройте под свой frontend
        // без SockJS для простоты
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Префикс для отправки сообщений клиенту (топики / очереди)
        registry.enableSimpleBroker("/topic", "/queue");
        // Префикс, с которого клиенты отправляют сообщения на сервер
        registry.setApplicationDestinationPrefixes("/app");
    }
}