package com.skilltrack.skilltrack_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserSkillResponse {
    private Long id;
    private String skillName;
    private int progress;
    private String level;


}
