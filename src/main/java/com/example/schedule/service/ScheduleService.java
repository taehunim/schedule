package com.example.schedule.service;

import com.example.schedule.domain.ScheduleObject;
import com.example.schedule.dto.RequestDto;
import com.example.schedule.dto.RequestDtoToUpdate;
import com.example.schedule.dto.ResponseDto;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ResponseDto saveSchedule(RequestDto dto) {
        LocalDateTime now = LocalDateTime.now();
        ScheduleObject scheduleObject = new ScheduleObject(
                dto.getTitle(),
                dto.getContents(),
                dto.getUserName(),
                dto.getPassword(),
                now,
                now
        );

        scheduleObject = scheduleRepository.saveSchedule(scheduleObject);
        return makeResponseDto(scheduleObject);
    }

    public ResponseDto findScheduleById(Long id) {
         if (scheduleRepository.findScheduleById(id).isEmpty()) {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exists id = " + id);
         }
        return new ResponseDto(scheduleRepository.findScheduleById(id).get());
    }

    public List<ResponseDto> findAllSchedules() {
        List<ScheduleObject> allSchedules = scheduleRepository.findAllSchedules();
        List<ResponseDto> responseDtoList = new ArrayList<>();
        for(ScheduleObject scheduleObject : allSchedules) {
            responseDtoList.add(new ResponseDto(scheduleObject));
        }
        return responseDtoList;
    }

    public List<ResponseDto> searchUserNameAndDate(String userName, LocalDate date) {
        List<ScheduleObject> scheduleObjects = scheduleRepository.searchUserNameAndDate(userName, date);
        List<ResponseDto> responseDtoList = new ArrayList<>();
        for(ScheduleObject scheduleObject : scheduleObjects) {
            responseDtoList.add(new ResponseDto(scheduleObject));
        }
        return responseDtoList;
    }

    public int updateSchedule(Long id, RequestDtoToUpdate dto) {
        LocalDateTime now = LocalDateTime.now();
        return scheduleRepository.updateSchedule(id, dto.getTitle(), dto.getContents(), dto.getPassword(), now);
    }

    public int removeSchedule(Long id) {
        return scheduleRepository.removeSchedule(id);
    }

    private ResponseDto makeResponseDto(ScheduleObject scheduleObject) {
        return new ResponseDto(
                scheduleObject.getScheduleId(),
                scheduleObject.getTitle(),
                scheduleObject.getContents(),
                scheduleObject.getUserName(),
                scheduleObject.getPostDate(),
                scheduleObject.getUpdateDate()
        );
    }
}

