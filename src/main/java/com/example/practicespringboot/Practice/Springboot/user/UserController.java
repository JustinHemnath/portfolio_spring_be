package com.example.practicespringboot.Practice.Springboot.user;

import com.example.practicespringboot.Practice.Springboot.dto.UserResponseDto;
import com.example.practicespringboot.Practice.Springboot.mapper.MessagesUtil;
import com.example.practicespringboot.Practice.Springboot.messages.MessageService;
import com.example.practicespringboot.Practice.Springboot.messages.Messages;
import com.example.practicespringboot.Practice.Springboot.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private UserService userService;
    private final MessageService messageService;

    UserController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/users/validateUser")
    public ResponseEntity<?> postMethodName(@RequestBody PostUserDto postUserDto) {
        if (postUserDto == null || postUserDto.getName() == null || postUserDto.getEmail() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "user object with name and email props is required");
        } else {
            Map<String, Object> metadata = new HashMap<>();

            List<Map<String, String>> users = userService.getUsers().stream()
                    .map(u -> Map.of("email", u.email, "name", u.name))
                    .collect(Collectors.toList());
            boolean userExists = users.stream().anyMatch(u -> u.get("email").equals(postUserDto.getEmail()));

            if (!userExists) {
                User newUser = new User();
                newUser.setEmail(postUserDto.getEmail());
                newUser.setName(postUserDto.getName());

                User userCreated = userService.saveUser(newUser);

                if (userCreated == null) {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Creation of user failed");
                } else {
                    users.add(Map.of("email", postUserDto.getEmail(), "name", postUserDto.getName()));
                    metadata.put("users", users);
                    return ResponseEntity.ok().body(
                            new UserResponseDto("registered", metadata));
                }
            } else {

                List<Messages> messages = messageService.getBySenderOrReceiver(
                        postUserDto.getEmail(),
                        postUserDto.getEmail());
                Object conversations = MessagesUtil.getModelledConversations(messages,
                        postUserDto.getEmail());
                metadata.put("conversations", conversations);
                metadata.put("users", users);

                return ResponseEntity.ok().body(
                        new UserResponseDto("validated", metadata));
            }
        }
    }
}
