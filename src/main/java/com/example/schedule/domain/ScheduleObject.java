package com.example.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor

public class ScheduleObject {
    private Long scheduleId;
    private String title;
    private String contents;
    private String userName;
    private String password;
    private LocalDateTime postDate;
    private LocalDateTime updateDate;


    public ScheduleObject(String title, String contents, String userName, String password, LocalDateTime postDate, LocalDateTime updateDate) {
        this.title = title;
        this.contents = contents;
        this.userName = userName;
        this.password = password;
        this.postDate = postDate;
        this.updateDate = updateDate;
    }
}
