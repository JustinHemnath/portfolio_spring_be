package com.example.practicespringboot.Practice.Springboot.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User {

    @Id
    @Column(name = "email")
    public String email;

    @Column(name = "name")
    public String name;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "User[email=%s, name='%s']",
                email, name);
    }

}
