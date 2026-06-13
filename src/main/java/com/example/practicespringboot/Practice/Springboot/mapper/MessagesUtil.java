package com.example.practicespringboot.Practice.Springboot.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.practicespringboot.Practice.Springboot.messages.Messages;

public class MessagesUtil {

    public static List<Map<String, Object>> getModelledConversations(List<Messages> messages, String currentUserEmail) {
        List<Map<String, Object>> finalConversationsArr = new ArrayList<>();

        if (messages.size() == 0) {
            return finalConversationsArr;
        }

        for (Messages message : messages) {
            String otherPersonEmail;
            String otherPersonName;

            if (currentUserEmail.equals(message.sender)) {
                otherPersonEmail = message.receiver;
                otherPersonName = message.receiver_name;
            } else {
                otherPersonEmail = message.sender;
                otherPersonName = message.sender_name;
            }

            if (finalConversationsArr.size() == 0) {
                finalConversationsArr.add(new HashMap<>(Map.of(
                        "otherPersonEmail", otherPersonEmail,
                        "otherPersonName", otherPersonName,
                        "messages", new ArrayList<>(List.of(message)))));
            } else {
                Integer convoIdx = -1;
                Integer itrIdx = 0;

                for (Map<String, Object> convo : finalConversationsArr) {
                    if (convo.get("otherPersonEmail").equals(otherPersonEmail)) {
                        convoIdx = convoIdx + 1;
                        break;
                    }
                    itrIdx++;
                }

                if (convoIdx == -1) {
                    finalConversationsArr.add(new HashMap<>(Map.of(
                            "otherPersonEmail", otherPersonEmail,
                            "otherPersonName", otherPersonName,
                            "messages", new ArrayList<>(List.of(message)))));
                } else {
                    Map<String, Object> matchingConvo = finalConversationsArr.get(convoIdx);
                    List<Object> newMessages = (List<Object>) matchingConvo.get("messages");
                    newMessages.add(message);
                }
            }
        }

        List<Map<String, Object>> modifiedConversations = finalConversationsArr.stream().map(c -> {
            List<Messages> convoMessages = (List<Messages>) c.get("messages");
            Messages lastMessage = convoMessages.get(convoMessages.size() - 1);
            c.put("lastMessage", lastMessage);
            return c;
        }).collect(Collectors.toList());

        return modifiedConversations;
    }
}
