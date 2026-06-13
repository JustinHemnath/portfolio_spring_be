package com.example.practicespringboot.Practice.Springboot.config;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponentsBuilder;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

        String email = request.getHeaders().getFirst("email");

        if (email == null || email.isBlank()) {
            URI uri = request.getURI();
            MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUri(uri).build().getQueryParams();
            email = queryParams.getFirst("email");
        }

        if (email == null || email.isBlank()) {
            return null;
        }

        return new StompPrincipal(email);
    }
}
