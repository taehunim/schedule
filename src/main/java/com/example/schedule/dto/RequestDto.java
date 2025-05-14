package com.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RequestDto {
    private String title;
    private String contents;
    private String userName;
    private String password;
}
