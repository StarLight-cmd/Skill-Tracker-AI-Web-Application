package com.skilltrack.skilltrack_backend.controller;

import com.skilltrack.skilltrack_backend.dto.UserSkillResponse;
import com.skilltrack.skilltrack_backend.model.Skill;
import com.skilltrack.skilltrack_backend.model.UserSkill;
import com.skilltrack.skilltrack_backend.repository.UserSkillRepository;
import com.skilltrack.skilltrack_backend.service.UserSkillService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserSkillController.class)
class UserSkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserSkillService userSkillService;

    @MockitoBean
    private UserSkillRepository userSkillRepository;

    @Test
    void shouldAddSkillToUser() throws Exception {
        UserSkill userSkill = new UserSkill();
        userSkill.setId(1L);
        userSkill.setLevel("Beginner");
        userSkill.setProgress(10);

        Mockito.when(userSkillService.addSkillToUser(1L, 2L, "Beginner"))
                .thenReturn(userSkill);

        mockMvc.perform(post("/api/user-skills")
                        .param("userId", "1")
                        .param("skillId", "2")
                        .param("level", "Beginner"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.level").value("Beginner"));
    }

    @Test
    void shouldReturnUserSkills() throws Exception {
        Skill skill = new Skill();
        skill.setName("Java");

        UserSkill userSkill = new UserSkill();
        userSkill.setId(1L);
        userSkill.setSkill(skill);
        userSkill.setProgress(50);
        userSkill.setLevel("Intermediate");

        Mockito.when(userSkillRepository.findByUserId(1L))
                .thenReturn(List.of(userSkill));

        mockMvc.perform(get("/api/user-skills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].skillName").value("Java"))
                .andExpect(jsonPath("$[0].progress").value(50))
                .andExpect(jsonPath("$[0].level").value("Intermediate"));
    }

    @Test
    void shouldUpdateProgress() throws Exception {
        UserSkillResponse response =
                new UserSkillResponse(1L, "Java", 80, "Advanced");

        Mockito.when(userSkillService.updateProgress(1L, 80, "Advanced"))
                .thenReturn(response);

        mockMvc.perform(patch("/api/user-skills/1/progress")
                        .param("progress", "80")
                        .param("level", "Advanced"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.progress").value(80))
                .andExpect(jsonPath("$.level").value("Advanced"));
    }

    @Test
    void shouldDeleteUserSkill() throws Exception {
        mockMvc.perform(delete("/api/user-skills/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(userSkillService).deleteUserSkill(1L);
    }
}
