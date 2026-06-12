package com.example.practicespringboot.Practice.Springboot.messages;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.practicespringboot.Practice.Springboot.config.WebSocketEventListener;
import com.example.practicespringboot.Practice.Springboot.dto.MessageDto;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MessageController {
    public MessageService messageService;
    private SimpMessagingTemplate messagingTemplate;
    private WebSocketEventListener webSocketEventListener;

    MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate,
            WebSocketEventListener webSocketEventListener) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
        this.webSocketEventListener = webSocketEventListener;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Messages>> getAllMessages() {
        return ResponseEntity.ok().body(messageService.getAllMessages());
    }

    @GetMapping("/messages/id")
    public ResponseEntity<List<Messages>> getBySenderAndReceiver() {
        return ResponseEntity.ok().body(
                messageService.getBySenderOrReceiver("justin.hemnath.96@gmail.com",
                        "justin.hemnath.96@gmail.com"));
    }

    @MessageMapping("/postMessage")
    public void send(
            MessageDto messageDto) {
        System.out.println("***********");
        System.out.println(messageDto.toString());
        System.out.println(webSocketEventListener.emailToSessionId);
        System.out.println("***********");

        String targetEmail = messageDto.getReceiver();
        String targetID = webSocketEventListener.emailToSessionId.get(targetEmail);

        System.out.println("Target: " + targetEmail + " " + targetID + " - message:  " + messageDto.message);
        messagingTemplate.convertAndSendToUser(
                targetID,
                "/listenMessage",
                messageDto);
    }
}
