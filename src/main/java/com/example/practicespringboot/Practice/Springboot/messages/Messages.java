package com.example.practicespringboot.Practice.Springboot.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Messages {

    @Id
    @Column(name = "id")
    public String id;

    @Column(name = "sender")
    public String sender;

    @Column(name = "receiver")
    public String receiver;

    @Column(name = "message")
    public String message;

    @Column(name = "sent_at")
    public String sent_at;

    @Column(name = "sender_name")
    public String sender_name;

    @Column(name = "receiver_name")
    public String receiver_name;

    @Override
    public String toString() {
        return String.format(
                "Messages[id=%s, sender='%s', receiver='%s', message='%s', sent_at='%s', sender_name='%s', receiver_name='%s']",
                id, sender, receiver, message, sent_at, sender_name, receiver_name);
    }
}
