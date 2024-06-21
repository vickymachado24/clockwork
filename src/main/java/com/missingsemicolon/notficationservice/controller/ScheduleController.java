package com.missingsemicolon.notficationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.missingsemicolon.notficationservice.dto.ScheduleDto;
import com.missingsemicolon.notficationservice.exception.DataInvalidException;
import com.missingsemicolon.notficationservice.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final Logger LOGGER = LoggerFactory.getLogger(ScheduleController.class);
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("")
    public ResponseEntity<ScheduleDto> createScheduleNotification(@RequestBody ScheduleDto schedule) throws DataInvalidException, JsonProcessingException {
        LOGGER.info("Creating a new schedule for manager id {} ", schedule.getManagerId());
        ScheduleDto scheduleDto = scheduleService.createScheduleNotification(schedule.getManagerId(), schedule);
        return new ResponseEntity<>(scheduleDto, HttpStatus.CREATED);
    }

    @GetMapping("/{managerId}")
    public ResponseEntity<List<ScheduleDto>> getAllScheduleNotification(@PathVariable Long managerId) throws DataInvalidException, JsonProcessingException {
        LOGGER.info("Getting all scheduled notifications for manager id {} ", managerId);
        List<ScheduleDto> scheduleDtoList = scheduleService.getListOfScheduledNotification(managerId);
        return new ResponseEntity<>(scheduleDtoList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> updateScheduledNotification( @PathVariable long id, @RequestBody ScheduleDto schedule) throws DataInvalidException, JsonProcessingException {
        LOGGER.info("Updating scheduled notification for manager id {} and notification id {} ", schedule.getManagerId(), id);
        ScheduleDto updatedScheduleDto = scheduleService.updateScheduledNotification(schedule.getManagerId(), id, schedule);
        return new ResponseEntity<>(updatedScheduleDto, HttpStatus.OK);
    }

    @DeleteMapping("/{managerId}/{id}")
    public ResponseEntity<Void> deleteScheduledNotification(@PathVariable Long managerId, @PathVariable long id) throws DataInvalidException {
        LOGGER.info("Deleting scheduled notification for manager id {} and notification id {} ", managerId, id);
        scheduleService.deleteScheduledNotification(managerId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
