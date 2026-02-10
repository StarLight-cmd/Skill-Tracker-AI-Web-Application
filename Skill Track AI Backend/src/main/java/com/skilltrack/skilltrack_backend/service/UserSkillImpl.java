package com.skilltrack.skilltrack_backend.service;

import com.skilltrack.skilltrack_backend.dto.UserSkillReportResponse;
import com.skilltrack.skilltrack_backend.dto.UserSkillResponse;
import com.skilltrack.skilltrack_backend.model.Skill;
import com.skilltrack.skilltrack_backend.model.User;
import com.skilltrack.skilltrack_backend.model.UserSkill;
import com.skilltrack.skilltrack_backend.repository.SkillRepository;
import com.skilltrack.skilltrack_backend.repository.UserRepository;
import com.skilltrack.skilltrack_backend.repository.UserSkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSkillImpl implements UserSkillService {
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final UserSkillRepository userSkillRepository;

    public UserSkillImpl(
            UserRepository userRepository,
            SkillRepository skillRepository,
            UserSkillRepository userSkillRepository
    ){
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.userSkillRepository = userSkillRepository;
    }

    public UserSkill addSkillToUser(Long userId, Long skillId, String level){
        if(userSkillRepository.existsByIdAndSkillId(userId, skillId)){
            throw new IllegalArgumentException("Skill already assigned to user");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        UserSkill userSkill = new UserSkill();
        userSkill.setUser(user);
        userSkill.setSkill(skill);
        userSkill.setLevel(level);
        userSkill.setProgress(0);

        return userSkillRepository.save(userSkill);
    }

    public List<UserSkillResponse> getUserSkills(Long userId){
        return userSkillRepository.findByUserId(userId)
                .stream()
                .map(us -> new UserSkillResponse(
                        us.getId(),
                        us.getSkill().getName(),
                        us.getProgress(),
                        us.getLevel()
                ))
                .toList();
    }

    public UserSkillResponse updateProgress(Long userSkillId, int progress, String level) {
        UserSkill us = userSkillRepository.findById(userSkillId)
                .orElseThrow(() -> new RuntimeException("User skill not found"));

        us.setProgress(progress);
        us.setLevel(level);

        UserSkill saved = userSkillRepository.save(us);

        return new UserSkillResponse(
                saved.getId(),
                saved.getSkill().getName(),
                saved.getProgress(),
                saved.getLevel()
        );

    }

    public void deleteUserSkill(Long id){
        if(!userSkillRepository.existsById(id)){
            throw  new RuntimeException("User skill not found");
        }

        userSkillRepository.deleteById(id);
    }

    public List<UserSkillReportResponse> getUserSkillReport(Long userId){
        return userSkillRepository.findByUserId(userId)
                .stream()
                .map(userSkill -> new UserSkillReportResponse(
                  userSkill.getSkill().getName(),
                  userSkill.getProgress(),
                  userSkill.getLevel(),
                  userSkill.getCreatedAt()
                )).toList();
    }

}
