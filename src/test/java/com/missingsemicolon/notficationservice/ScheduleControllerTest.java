package com.missingsemicolon.notficationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.missingsemicolon.notficationservice.controller.ScheduleController;
import com.missingsemicolon.notficationservice.dto.ScheduleDto;
import com.missingsemicolon.notficationservice.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @Test
    public void testCreateScheduleNotification() throws Exception {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setManagerId("1");
        scheduleDto.setBusinessId("123");
        scheduleDto.setStartDate(LocalDateTime.now());
        scheduleDto.setEndDate(LocalDateTime.now().plusDays(1));
        scheduleDto.setFrequency("DAILY");
        scheduleDto.setPayload("payload");

        when(scheduleService.createScheduleNotification(anyLong(), any(ScheduleDto.class))).thenReturn(scheduleDto);

        mockMvc.perform(post("/api/schedule/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(scheduleDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.managerId").value("1"));
    }

}