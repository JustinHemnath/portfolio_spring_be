package com.example.practicespringboot.Practice.Springboot.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Conversation {
    public String otherPersonEmail;
    public String otherPersonName;
    public List<Map<String, Object>> messages;
}
