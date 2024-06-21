package com.missingsemicolon.notficationservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.missingsemicolon.notficationservice.dto.ScheduleDto;
import com.missingsemicolon.notficationservice.entity.Schedule;
import com.missingsemicolon.notficationservice.enums.SchedulingFrequency;
import com.missingsemicolon.notficationservice.exception.DataInvalidException;
import com.missingsemicolon.notficationservice.repository.ScheduleRepository;
import com.missingsemicolon.notficationservice.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @MockBean
    private ScheduleRepository scheduleRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateScheduleNotification() throws DataInvalidException, JsonProcessingException {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setManagerId("1");
        scheduleDto.setBusinessId("123");
        scheduleDto.setStartDate(LocalDateTime.now());
        scheduleDto.setEndDate(LocalDateTime.now().plusDays(1));
        scheduleDto.setFrequency("DAILY");
        scheduleDto.setPayload("{ \"dummy\":\"payload\",\"dummy2\":\"payload2\"}");

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setManagerId("1");
        schedule.setBusinessId("123");
        schedule.setStartDate(LocalDateTime.now());
        schedule.setEndDate(LocalDateTime.now().plusDays(1));
        schedule.setFrequency(SchedulingFrequency.DAILY);
        schedule.setPayload(objectMapper.writeValueAsString(scheduleDto.getPayload()));

        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        ScheduleDto createdSchedule = scheduleService.createScheduleNotification(1L, scheduleDto);

        assertEquals("1", createdSchedule.getManagerId());
    }


}