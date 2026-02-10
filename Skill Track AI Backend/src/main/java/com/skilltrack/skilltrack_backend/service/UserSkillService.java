package com.skilltrack.skilltrack_backend.service;

import com.skilltrack.skilltrack_backend.dto.UserSkillReportResponse;
import com.skilltrack.skilltrack_backend.dto.UserSkillResponse;
import com.skilltrack.skilltrack_backend.model.Skill;
import com.skilltrack.skilltrack_backend.model.User;
import com.skilltrack.skilltrack_backend.model.UserSkill;
import com.skilltrack.skilltrack_backend.repository.SkillRepository;
import com.skilltrack.skilltrack_backend.repository.UserRepository;
import com.skilltrack.skilltrack_backend.repository.UserSkillRepository;

import java.util.List;

public interface UserSkillService {
    UserSkill addSkillToUser(Long userId, Long skillId, String level);
    List<UserSkillResponse> getUserSkills(Long userId);
    UserSkillResponse updateProgress(Long userSkillId, int progress, String level);
    void deleteUserSkill(Long userSkillId);
    List<UserSkillReportResponse> getUserSkillReport(Long userId);
}
