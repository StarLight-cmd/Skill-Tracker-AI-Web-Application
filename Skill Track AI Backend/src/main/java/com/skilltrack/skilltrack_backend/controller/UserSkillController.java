package com.skilltrack.skilltrack_backend.controller;

import com.skilltrack.skilltrack_backend.dto.UserSkillResponse;
import com.skilltrack.skilltrack_backend.model.UserSkill;
import com.skilltrack.skilltrack_backend.repository.UserSkillRepository;
import com.skilltrack.skilltrack_backend.service.UserSkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-skills")
public class UserSkillController {

    private final UserSkillService userSkillService;
    private final UserSkillRepository userSkillRepository;

    public UserSkillController(UserSkillService userSkillService, UserSkillRepository userSkillRepository) {
        this.userSkillService = userSkillService;
        this.userSkillRepository = userSkillRepository;
    }

    @PostMapping
    public ResponseEntity<UserSkill> addSkill(
            @RequestParam Long userId,
            @RequestParam Long skillId,
            @RequestParam String level
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userSkillService.addSkillToUser(userId, skillId, level));
    }

    @GetMapping("/{userId}")
    public List<UserSkillResponse> getUserSkills(@PathVariable Long userId){
        return userSkillRepository.findByUserId(userId)
                .stream()
                .map(userSkill -> new UserSkillResponse(
                        userSkill.getId(),
                        userSkill.getSkill().getName(),
                        userSkill.getProgress(),
                        userSkill.getLevel()
                ))
                .toList();
    }

    @PatchMapping("/{id}/progress")
    public ResponseEntity<UserSkillResponse> updateProgress(
            @PathVariable Long id,
            @RequestParam int progress,
            @RequestParam String level
    ){
        return ResponseEntity.ok(
                userSkillService.updateProgress(id, progress,level)
        );
    }

    @DeleteMapping("/{userSkillId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserSkill(@PathVariable Long userSkillId){
        userSkillService.deleteUserSkill(userSkillId);
    }
}
