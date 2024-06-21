package com.missingsemicolon.notficationservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.missingsemicolon.notficationservice.exception.DataInvalidException;
import com.missingsemicolon.notficationservice.service.ScheduleService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationJob implements Job {

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            scheduleService.processSchedules();
        } catch (DataInvalidException e) {
            throw new JobExecutionException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
