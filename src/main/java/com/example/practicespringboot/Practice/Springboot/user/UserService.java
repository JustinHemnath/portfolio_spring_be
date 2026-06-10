package com.example.practicespringboot.Practice.Springboot.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
