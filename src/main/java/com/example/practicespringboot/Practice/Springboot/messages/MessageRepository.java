package com.example.practicespringboot.Practice.Springboot.messages;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Messages, String> {

    public List<Messages> getBySenderOrReceiver(String sender, String receiver);
}
