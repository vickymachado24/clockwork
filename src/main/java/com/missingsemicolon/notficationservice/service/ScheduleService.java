package com.missingsemicolon.notficationservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.missingsemicolon.notficationservice.dto.ScheduleDto;
import com.missingsemicolon.notficationservice.entity.Schedule;
import com.missingsemicolon.notficationservice.exception.DataInvalidException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ScheduleService {

    public ScheduleDto createScheduleNotification(Long managerId, ScheduleDto scheduleDto) throws
            DataInvalidException, JsonProcessingException;

    public ScheduleDto updateScheduledNotification(Long managerId,Long scheduleId, ScheduleDto scheduleDto) throws
            DataInvalidException, JsonProcessingException;

    public List<ScheduleDto> getListOfScheduledNotification(Long managerId) throws
            DataInvalidException, JsonProcessingException;

    public void deleteScheduledNotification(Long managerId, Long scheduleId) throws
            DataInvalidException;

    public void processSchedules() throws DataInvalidException, JsonProcessingException;

}
