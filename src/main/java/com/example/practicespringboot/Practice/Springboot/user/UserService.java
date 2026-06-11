package com.example.practicespringboot.Practice.Springboot.user;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
