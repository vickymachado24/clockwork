package com.missingsemicolon.notficationservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.missingsemicolon.notficationservice.dto.ScheduleDto;
import com.missingsemicolon.notficationservice.entity.Schedule;
import com.missingsemicolon.notficationservice.enums.SchedulingFrequency;
import com.missingsemicolon.notficationservice.exception.DataInvalidException;
import com.missingsemicolon.notficationservice.repository.ScheduleRepository;
import com.missingsemicolon.notficationservice.service.NotificationService;
import com.missingsemicolon.notficationservice.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final Logger LOGGER = LoggerFactory.getLogger(ScheduleServiceImpl.class);
    private final ScheduleRepository scheduleRepository;
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, NotificationService notificationService) {
        this.scheduleRepository = scheduleRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    @Override
    public ScheduleDto createScheduleNotification(Long managerId, ScheduleDto scheduleDto) throws DataInvalidException, JsonProcessingException {
        Schedule scheduleFromRequest = createScheduleFromDto(scheduleDto);
        Schedule savedSchedule = scheduleRepository.save(scheduleFromRequest);
        return createDtoFromSchedule(savedSchedule);
    }

    @Transactional
    @Override
    public ScheduleDto updateScheduledNotification(Long managerId, Long scheduleId, ScheduleDto scheduleDto) throws DataInvalidException, JsonProcessingException {
        Schedule existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new DataInvalidException("Schedule not found"));

        existingSchedule.setBusinessId(scheduleDto.getBusinessId());
        existingSchedule.setManagerId(scheduleDto.getManagerId());
        existingSchedule.setStartDate(scheduleDto.getStartDate());
        existingSchedule.setEndDate(scheduleDto.getEndDate());
        existingSchedule.setFrequency(SchedulingFrequency.findByName(scheduleDto.getFrequency().toUpperCase()).name().toUpperCase());
        existingSchedule.setPayload(scheduleDto.getPayload());
        existingSchedule.setNextRun(calculateNextRun(scheduleDto, existingSchedule.getFrequency()));

        Schedule updatedSchedule = scheduleRepository.save(existingSchedule);
        return createDtoFromSchedule(updatedSchedule);
    }

    @Override
    public List<ScheduleDto> getListOfScheduledNotification(Long managerId) throws DataInvalidException, JsonProcessingException {
        List<Schedule> schedules = scheduleRepository.findAllByManagerId(managerId);
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDtos.add(createDtoFromSchedule(schedule));
        }
        return scheduleDtos;
    }

    @Transactional
    @Override
    public void deleteScheduledNotification(Long managerId, Long scheduleId) throws DataInvalidException {
        scheduleRepository.deleteById(scheduleId);
    }

    private Schedule createScheduleFromDto(ScheduleDto scheduleDto) throws DataInvalidException, JsonProcessingException {
        Schedule schedule = new Schedule();
        schedule.setBusinessId(scheduleDto.getBusinessId());
        schedule.setManagerId(scheduleDto.getManagerId());
        schedule.setStartDate(scheduleDto.getStartDate());
        schedule.setEndDate(scheduleDto.getEndDate());
        schedule.setPayload(scheduleDto.getPayload());
        schedule.setFrequency(SchedulingFrequency.findByName(scheduleDto.getFrequency().toUpperCase()).name().toUpperCase());
        schedule.setNextRun(calculateNextRun(scheduleDto, schedule.getFrequency()));
        return schedule;
    }

    private ScheduleDto createDtoFromSchedule(Schedule schedule) throws JsonProcessingException {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(schedule.getId());
        scheduleDto.setBusinessId(schedule.getBusinessId());
        scheduleDto.setManagerId(schedule.getManagerId());
        scheduleDto.setStartDate(schedule.getStartDate());
        scheduleDto.setEndDate(schedule.getEndDate());
        scheduleDto.setFrequency(schedule.getFrequency().toString());
        scheduleDto.setPayload(schedule.getPayload(Object.class));
        return scheduleDto;
    }

    private LocalDateTime calculateNextRun(ScheduleDto scheduleDto, String frequency) throws DataInvalidException {
        switch (frequency) {
            case "DAILY":
                return scheduleDto.getStartDate().plusDays(1);
            case "WEEKLY":
                return scheduleDto.getStartDate().plusWeeks(1);
            case "HOURLY":
                return scheduleDto.getStartDate().plusHours(1);
            case "MONTHLY":
                return scheduleDto.getStartDate().plusMonths(1);
            case "QUARTERLY":
                return scheduleDto.getStartDate().plusMonths(3);
            case "YEARLY":
                return scheduleDto.getStartDate().plusYears(1);
            case "CUSTOM":
                return scheduleDto.getSpecificDate();
            default:
                throw new DataInvalidException("Incorrect Notification Frequency or Specific time provided");
        }
    }

    @Transactional
    public void processSchedules() throws DataInvalidException, JsonProcessingException {
        List<Schedule> schedules = scheduleRepository.findByNextRunBefore(LocalDateTime.now());
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDtos.add(createDtoFromSchedule(schedule));
            schedule.setNextRun(calculateNextRun(createDtoFromSchedule(schedule), schedule.getFrequency()));
        }
        notificationService.sendNotification(scheduleDtos);
        scheduleRepository.saveAll(schedules);
    }
}
