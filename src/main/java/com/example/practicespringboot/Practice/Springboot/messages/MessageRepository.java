package com.example.practicespringboot.Practice.Springboot.messages;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Messages, String> {

}
