package com.missingsemicolon.notficationservice.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDto implements Serializable {

    private Long id;

    private Long businessId;
    private Long managerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String frequency;
    private LocalDateTime specificDate;
    private Object payload;


}
