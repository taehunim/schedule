package com.example.schedule.controller;

import com.example.schedule.domain.ScheduleObject;
import com.example.schedule.dto.RequestDto;
import com.example.schedule.dto.ResponseDto;
import com.example.schedule.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class Schedulecontroller {

    private final ScheduleService scheduleService;

    public Schedulecontroller(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @PostMapping
    public ResponseEntity<ResponseDto> createSchedule(@RequestBody RequestDto dto) {
        ResponseDto responseDto = scheduleService.saveSchedule(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 전체 조회
    @GetMapping
    public List<ResponseDto> findAllSchedules() {
        return scheduleService.findAllSchedules();
    }


    // 단 건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id),HttpStatus.OK);
    }

    // 날짜 이름으로 조회
    @GetMapping("/search")
    public ResponseEntity<List> searchUserNameAndDate(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<ResponseDto> results = scheduleService.searchUserNameAndDate(userName, date);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

}
