package com.skilltrack.skilltrack_backend.repository;

import com.skilltrack.skilltrack_backend.model.User;
import com.skilltrack.skilltrack_backend.model.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    boolean existsByIdAndSkillId(Long id, Long skillId);
    List<UserSkill> findByUserId(Long userId);

    Long user(User user);

}
