package com.example.practicespringboot.Practice.Springboot.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

        private final Map<String, String> sessions = new ConcurrentHashMap<>();
        public Map<String, String> emailToSessionId = new ConcurrentHashMap<>();
        public Map<String, String> sessionIdToEmail = new ConcurrentHashMap<>();

        @EventListener
        public void handleSessionConnected(
                        SessionConnectEvent event) {

                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
                String sessionId = accessor.getSessionId();
                sessions.put(sessionId, sessionId);

                String email = null;
                if (accessor.getUser() != null) {
                        email = accessor.getUser().getName();
                }
                if (email == null) {
                        email = accessor.getFirstNativeHeader("email");
                }

                if (email != null) {
                        emailToSessionId.put(email, sessionId);
                        sessionIdToEmail.put(sessionId, email);
                        System.out.println("Connected: " + email + " - " + sessionId);
                } else {
                        System.out.println("Connected session has no email principal or header: " + sessionId);
                }
        }

        @EventListener
        public void handleSessionDisconnected(
                        SessionDisconnectEvent event) {

                String sessionId = event.getSessionId();
                sessions.remove(sessionId);
                String email = sessionIdToEmail.remove(sessionId);

                if (email != null) {
                        emailToSessionId.remove(email);
                }

                System.out.println("Disconnected: " + sessionId);
        }
}