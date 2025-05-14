package com.example.schedule.dto;

import com.example.schedule.domain.ScheduleObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private Long scheduleId;
    private String title;
    private String contents;
    private String userName;
    private LocalDateTime postDate;
    private LocalDateTime updateDate;

    public ResponseDto(ScheduleObject scheduleObject) {
        this.scheduleId = scheduleObject.getScheduleId();
        this.title = scheduleObject.getTitle();
        this.contents = scheduleObject.getContents();
        this.userName = scheduleObject.getUserName();
        this.postDate = scheduleObject.getPostDate();
        this.updateDate = scheduleObject.getUpdateDate();
    }
}
