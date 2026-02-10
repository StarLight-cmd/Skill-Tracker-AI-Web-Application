package com.skilltrack.skilltrack_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserSkillReportResponse {
    private String skillName;
    private int progress;
    private String level;
    private LocalDateTime createdAt;
}
