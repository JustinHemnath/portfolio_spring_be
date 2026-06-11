package com.example.practicespringboot.Practice.Springboot.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PostUserDto {
    public UserObj user;

    PostUserDto(UserObj user) {
        this.user = new UserObj(user.getEmail(), user.getName());
    }

    String getEmail() {
        return this.user.email;
    }

    String getName() {
        return this.user.name;
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class UserObj {
    public String email;
    public String name;
}
