package com.skilltrack.skilltrack_backend.repository;

import com.skilltrack.skilltrack_backend.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    boolean existsByNameIgnoreCase(String name);
}
