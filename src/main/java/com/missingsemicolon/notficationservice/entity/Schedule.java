package com.missingsemicolon.notficationservice.entity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.missingsemicolon.notficationservice.enums.SchedulingFrequency;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long businessId;
    private Long managerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String frequency;// DAILY, WEEKLY, CUSTOM
    private LocalDateTime creationTime;
    private LocalDateTime modificationDateTime;
    private String payload;
    private LocalDateTime nextRun;

    @PrePersist
    protected void onCreate() {
        creationTime = LocalDateTime.now();
        modificationDateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modificationDateTime = LocalDateTime.now();
    }

    public void setPayload(Object payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.payload = objectMapper.writeValueAsString(payload);
    }

    public <T> T getPayload(Class<T> type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(this.payload, type);
    }

}
