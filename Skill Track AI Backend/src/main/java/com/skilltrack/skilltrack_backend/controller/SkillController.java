package com.skilltrack.skilltrack_backend.controller;

import com.skilltrack.skilltrack_backend.dto.SkillResponse;
import com.skilltrack.skilltrack_backend.model.Skill;
import com.skilltrack.skilltrack_backend.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService){
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody @Valid Skill skill){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(skillService.createSkill(skill));
    }

    @GetMapping
    public List<SkillResponse> getAllSkills(){
        return skillService.getAllSkills();
    }

    @GetMapping("/{id}")
    public Skill getSkill(@PathVariable Long id){
        return skillService.getSkillById(id);
    }
}
