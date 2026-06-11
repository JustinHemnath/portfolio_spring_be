package com.example.practicespringboot.Practice.Springboot.messages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

    @Autowired
    public MessageService messageService;

    MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Messages>> getAllMessages() {
        return ResponseEntity.ok().body(messageService.getAllMessages());
    }

}
