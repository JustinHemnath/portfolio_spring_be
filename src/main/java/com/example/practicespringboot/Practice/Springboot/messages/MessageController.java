package com.example.practicespringboot.Practice.Springboot.messages;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<List<Messages>> getBySenderAndReceiver(@RequestParam String email) {
        return ResponseEntity.ok().body(messageService.getBySenderOrReceiver(email, email));
    }

    @MessageMapping("/postMessage")
    public void send(MessageDto messageDto, Principal principal) {
        System.out.println("***********");
        System.out.println("Sender principal: " + (principal != null ? principal.getName() : "null"));
        System.out.println(messageDto.toString());
        // System.out.println(webSocketEventListener.emailToSessionId);
        System.out.println("***********");

        String targetEmail = messageDto.getReceiver();
        String targetID = webSocketEventListener.emailToSessionId.get(targetEmail);

        System.out.println("Target: " + targetEmail + " " + targetID + " - message:  " + messageDto.getMessage());

        Messages newMessages = new Messages();
        newMessages.setId(messageDto.getId());
        newMessages.setSender(messageDto.getSender());
        newMessages.setSender_name(messageDto.getSender_name());
        newMessages.setReceiver(messageDto.getReceiver());
        newMessages.setReceiver_name(messageDto.getReceiver_name());
        newMessages.setMessage(messageDto.getMessage());
        newMessages.setSent_at(messageDto.getSent_at());

        Messages savedMessage = messageService.saveMessage(newMessages);

        if (savedMessage instanceof Messages) {
            messagingTemplate.convertAndSendToUser(
                    targetEmail,
                    "/queue/listenMessage",
                    messageDto);
        }
    }
}
