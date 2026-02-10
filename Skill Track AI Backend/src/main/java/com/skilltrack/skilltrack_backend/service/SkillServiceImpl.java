package com.skilltrack.skilltrack_backend.service;

import com.skilltrack.skilltrack_backend.dto.SkillResponse;
import com.skilltrack.skilltrack_backend.model.Skill;
import com.skilltrack.skilltrack_backend.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository){
        this.skillRepository = skillRepository;
    }

    public Skill createSkill(Skill skill){
        if(skillRepository.existsByNameIgnoreCase(skill.getName())){
            throw new IllegalArgumentException("Skill already exists!");
        }
        return skillRepository.save(skill);
    }

    public List<SkillResponse> getAllSkills(){
        return skillRepository.findAll().stream().map(
                skill -> new SkillResponse(
                        skill.getId(),
                        skill.getName(),
                        skill.getDescription()
                )
        ).toList();
    }

    public Skill getSkillById(Long id){
        return skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found!"));
    }
}
