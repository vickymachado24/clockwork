package com.missingsemicolon.notficationservice.service;

import com.missingsemicolon.notficationservice.dto.ScheduleDto;
import com.missingsemicolon.notficationservice.entity.Schedule;

import java.util.List;

public interface NotificationService {

    public void sendNotification(List<ScheduleDto> scheduleDtoList);
}
