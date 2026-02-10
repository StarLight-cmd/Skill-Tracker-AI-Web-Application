package com.skilltrack.skilltrack_backend.controller;

import com.skilltrack.skilltrack_backend.dto.SkillResponse;
import com.skilltrack.skilltrack_backend.model.Skill;
import com.skilltrack.skilltrack_backend.service.SkillService;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SkillController.class)
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SkillService skillService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateSkill() throws Exception {
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setName("Java");

        Mockito.when(skillService.createSkill(Mockito.any(Skill.class)))
                .thenReturn(skill);

        Skill request = new Skill();
        request.setName("Java");

        mockMvc.perform(post("/api/skills")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void shouldReturnAllSkills() throws Exception {
        SkillResponse skill = new SkillResponse(1L, "Java", "ms");

        Mockito.when(skillService.getAllSkills())
                .thenReturn(List.of(skill));

        mockMvc.perform(get("/api/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Java"));
    }

    @Test
    void shouldReturnSkillById() throws Exception {
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setName("Java");

        Mockito.when(skillService.getSkillById(1L))
                .thenReturn(skill);

        mockMvc.perform(get("/api/skills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java"));
    }
}

