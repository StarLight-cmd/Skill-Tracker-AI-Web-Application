package com.skilltrack.skilltrack_backend.Service;
import com.skilltrack.skilltrack_backend.dto.SkillResponse;
import com.skilltrack.skilltrack_backend.model.Skill;
import com.skilltrack.skilltrack_backend.repository.SkillRepository;
import com.skilltrack.skilltrack_backend.service.SkillServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillServiceImplTest {
    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillServiceImpl skillService;

    @Test
    void shouldCreateSkill(){
        Skill skill = new Skill();
        skill.setName("Java");
        skill.setDescription("ms");

        when(skillRepository.existsByNameIgnoreCase("Java")).thenReturn(false);

        when(skillRepository.save(skill)).thenReturn(skill);

        Skill saved = skillService.createSkill(skill);

        assertEquals("Java", saved.getName());
        verify(skillRepository).save(skill);
    }

    @Test
    void shouldThrowExceptionWhenSkillExists(){
        Skill skill = new Skill();
        skill.setName("Java");

        when(skillRepository.existsByNameIgnoreCase("Java")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->skillService.createSkill(skill));

        assertEquals("Skill already exists!", exception.getMessage());

        verify(skillRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllSkillsAsResponse(){
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setName("Spring Boot");
        skill.setDescription("Backend framework");

        when(skillRepository.findAll()).thenReturn(List.of(skill));

        List<SkillResponse> skills = skillService.getAllSkills();
        assertEquals(1, skills.size());
        assertEquals("Spring Boot", skills.get(0).getName());
        assertEquals("Backend framework", skills.get(0).getDescription());
    }

    @Test
    void shouldReturnSkillById(){
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setName("React");

        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));

        Skill result = skillService.getSkillById(1L);

        assertEquals("React", result.getName());
    }

    @Test
    void ShouldThrowExceptionWhenSkillNotFound(){
        when(skillRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, ()-> skillService.getSkillById(1L));

        assertEquals("Skill not found!", exception.getMessage());
    }
}
