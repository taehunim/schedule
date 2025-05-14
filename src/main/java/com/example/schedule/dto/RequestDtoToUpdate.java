package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestDtoToUpdate {
    private String title;
    private String contents;
    private String password;
}
