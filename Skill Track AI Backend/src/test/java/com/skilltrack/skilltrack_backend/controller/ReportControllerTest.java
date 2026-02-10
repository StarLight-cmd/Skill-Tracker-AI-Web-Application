package com.skilltrack.skilltrack_backend.controller;

import com.skilltrack.skilltrack_backend.dto.UserSkillReportResponse;
import com.skilltrack.skilltrack_backend.service.UserSkillService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserSkillService userSkillService;

    @Test
    void shouldReturnUserSkillReport() throws Exception {

        UserSkillReportResponse report =
                new UserSkillReportResponse(
                        "Java",
                        75,
                        "Advanced",
                        LocalDateTime.now()
                );

        Mockito.when(userSkillService.getUserSkillReport(1L))
                .thenReturn(List.of(report));

        mockMvc.perform(get("/api/reports/skills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].skillName").value("Java"))
                .andExpect(jsonPath("$[0].progress").value(75))
                .andExpect(jsonPath("$[0].level").value("Advanced"));
    }
}
