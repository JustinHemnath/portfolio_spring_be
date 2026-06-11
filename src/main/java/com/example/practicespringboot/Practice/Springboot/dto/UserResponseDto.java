package com.example.practicespringboot.Practice.Springboot.dto;

import java.util.List;
import java.util.Map;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    public String action;
    public Map<String, Object> metaData;
}
