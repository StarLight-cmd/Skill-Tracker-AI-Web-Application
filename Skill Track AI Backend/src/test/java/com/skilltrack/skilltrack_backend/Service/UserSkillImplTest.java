package com.skilltrack.skilltrack_backend.Service;

import com.skilltrack.skilltrack_backend.dto.UserSkillReportResponse;
import com.skilltrack.skilltrack_backend.dto.UserSkillResponse;
import com.skilltrack.skilltrack_backend.model.Skill;
import com.skilltrack.skilltrack_backend.model.User;
import com.skilltrack.skilltrack_backend.model.UserSkill;
import com.skilltrack.skilltrack_backend.repository.SkillRepository;
import com.skilltrack.skilltrack_backend.repository.UserRepository;
import com.skilltrack.skilltrack_backend.repository.UserSkillRepository;
import com.skilltrack.skilltrack_backend.service.UserSkillImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSkillImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private UserSkillRepository userSkillRepository;

    @InjectMocks
    private UserSkillImpl userSkillService;

    @Test
    void shouldAddSkillToUser() {

        User user = new User();
        user.setId(1L);

        Skill skill = new Skill();
        skill.setId(10L);
        skill.setName("Java");

        when(userSkillRepository.existsByIdAndSkillId(1L, 10L))
                .thenReturn(false);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(skillRepository.findById(10L))
                .thenReturn(Optional.of(skill));

        when(userSkillRepository.save(any(UserSkill.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserSkill result =
                userSkillService.addSkillToUser(1L, 10L, "Beginner");

        assertEquals("Beginner", result.getLevel());
        assertEquals(0, result.getProgress());
    }

    @Test
    void shouldThrowExceptionWhenSkillAlreadyAssigned() {

        when(userSkillRepository.existsByIdAndSkillId(1L, 10L))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> userSkillService.addSkillToUser(1L, 10L, "Beginner"));

        verify(userSkillRepository, never()).save(any());
    }

    @Test
    void shouldReturnUserSkills() {

        Skill skill = new Skill();
        skill.setName("Spring");

        UserSkill userSkill = new UserSkill();
        userSkill.setId(5L);
        userSkill.setSkill(skill);
        userSkill.setProgress(50);
        userSkill.setLevel("Intermediate");

        when(userSkillRepository.findByUserId(1L))
                .thenReturn(List.of(userSkill));

        List<UserSkillResponse> result =
                userSkillService.getUserSkills(1L);

        assertEquals(1, result.size());
        assertEquals("Spring", result.get(0).getSkillName());
        assertEquals(50, result.get(0).getProgress());
    }

    @Test
    void shouldUpdateProgress() {

        Skill skill = new Skill();
        skill.setName("React");

        UserSkill userSkill = new UserSkill();
        userSkill.setId(2L);
        userSkill.setSkill(skill);
        userSkill.setProgress(10);
        userSkill.setLevel("Beginner");

        when(userSkillRepository.findById(2L))
                .thenReturn(Optional.of(userSkill));

        when(userSkillRepository.save(userSkill))
                .thenReturn(userSkill);

        UserSkillResponse result =
                userSkillService.updateProgress(2L, 80, "Advanced");

        assertEquals(80, result.getProgress());
        assertEquals("Advanced", result.getLevel());
    }

    @Test
    void shouldDeleteUserSkill() {

        when(userSkillRepository.existsById(3L))
                .thenReturn(true);

        userSkillService.deleteUserSkill(3L);

        verify(userSkillRepository).deleteById(3L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingMissingSkill() {

        when(userSkillRepository.existsById(3L))
                .thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> userSkillService.deleteUserSkill(3L));
    }

    @Test
    void shouldReturnUserSkillReport() {

        Skill skill = new Skill();
        skill.setName("Java");

        UserSkill userSkill = new UserSkill();
        userSkill.setSkill(skill);
        userSkill.setProgress(70);
        userSkill.setLevel("Advanced");
        userSkill.setCreatedAt(LocalDateTime.now());

        when(userSkillRepository.findByUserId(1L))
                .thenReturn(List.of(userSkill));

        List<UserSkillReportResponse> report =
                userSkillService.getUserSkillReport(1L);

        assertEquals(1, report.size());
        assertEquals("Java", report.get(0).getSkillName());
        assertEquals(70, report.get(0).getProgress());
    }
}
