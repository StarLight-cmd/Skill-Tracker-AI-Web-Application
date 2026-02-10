package com.skilltrack.skilltrack_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SkillResponse {
    private Long id;
    private String name;
    private String description;
}
