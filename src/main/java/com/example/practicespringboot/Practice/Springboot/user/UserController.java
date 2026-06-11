package com.example.practicespringboot.Practice.Springboot.user;

import com.example.practicespringboot.Practice.Springboot.dto.UserResponseDto;
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

    @Autowired
    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/validateUser")
    public ResponseEntity<?> postMethodName(@RequestBody PostUserDto postUserDto) {
        if (postUserDto == null || postUserDto.name() == null || postUserDto.email() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "user object with name and email props is required");
        } else {
            Map<String, Object> metadata = new HashMap<>();

            List<Map<String, String>> users = userService.getUsers().stream()
                    .map(u -> Map.of("email", u.email, "name", u.name))
                    .collect(Collectors.toList());
            boolean userExists = users.stream().anyMatch(u -> u.get("email").equals(postUserDto.email()));

            System.out.println(users);
            System.out.println(userExists);

            if (!userExists) {
                User newUser = new User();
                newUser.setEmail(postUserDto.email());
                newUser.setName(postUserDto.name());

                User userCreated = userService.saveUser(newUser);

                if (userCreated == null) {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Creation of user failed");
                } else {
                    users.add(Map.of("email", postUserDto.email(), "name", postUserDto.name()));
                    metadata.put("users", users);
                    return ResponseEntity.ok().body(
                            new UserResponseDto("registered", metadata));
                }
            } else {

                metadata.put("users", users);
                // metadata.put("conversations", conversations)

                return ResponseEntity.ok().body(
                        new UserResponseDto("validated", metadata));
            }
        }
    }
}
