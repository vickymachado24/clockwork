package com.missingsemicolon.notficationservice.dto;

public class NotificationRequestDto {


    private Long managerId;
    private ScheduleDto ScheduleDto;

    public NotificationRequestDto(Long managerId, ScheduleDto scheduleDto) {
        this.managerId = managerId;
        this.ScheduleDto = scheduleDto;
    }


}
