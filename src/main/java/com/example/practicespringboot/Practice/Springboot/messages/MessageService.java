package com.example.practicespringboot.Practice.Springboot.messages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    public MessageRepository messageRepository;

    public List<Messages> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Messages> getBySenderOrReceiver(String sender, String receiver) {
        return messageRepository.getBySenderOrReceiver(sender, receiver);
    }

}
