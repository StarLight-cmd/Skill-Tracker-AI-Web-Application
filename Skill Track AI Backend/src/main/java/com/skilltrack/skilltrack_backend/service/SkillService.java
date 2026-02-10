package com.skilltrack.skilltrack_backend.service;

import com.skilltrack.skilltrack_backend.dto.SkillResponse;
import com.skilltrack.skilltrack_backend.model.Skill;

import java.util.List;

public interface SkillService {
    Skill createSkill(Skill skill);
    List<SkillResponse> getAllSkills();
    Skill getSkillById(Long Id);
}
