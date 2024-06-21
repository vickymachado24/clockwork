package com.missingsemicolon.notficationservice.service.impl;

import com.missingsemicolon.notficationservice.dto.NotificationRequestDto;
import com.missingsemicolon.notficationservice.dto.ScheduleDto;
import com.missingsemicolon.notficationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final RestTemplate restTemplate;

    @Autowired
    public NotificationServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public void sendNotification(List<ScheduleDto> scheduleDtoList) {
        String url = "http://notification-service/api/notify";
        for (ScheduleDto scheduleDto : scheduleDtoList) {
            NotificationRequestDto request = new NotificationRequestDto(scheduleDto.getManagerId(), scheduleDto);
            restTemplate.postForEntity(url, request, Void.class);
        }
    }
}

