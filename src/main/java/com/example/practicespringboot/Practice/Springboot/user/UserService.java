package com.example.practicespringboot.Practice.Springboot.user;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practicespringboot.Practice.Springboot.messages.MessageRepository;
import com.example.practicespringboot.Practice.Springboot.messages.Messages;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public MessageRepository messageRepository;

    UserService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String Id) {
        return userRepository.findById(Id);
    }

    public User saveUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public List<Messages> getBySenderOrReceiver(String sender, String receiver) {
        return messageRepository.getBySenderOrReceiver(sender, receiver);
    }

}
