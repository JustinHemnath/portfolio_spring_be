package com.example.practicespringboot.Practice.Springboot.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    public String id;
    public String sender;
    public String receiver;
    public String message;
    public String sent_at;
    public String sender_name;
    public String receiver_name;
}
