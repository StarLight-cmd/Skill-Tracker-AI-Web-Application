package com.skilltrack.skilltrack_backend.controller;

import com.skilltrack.skilltrack_backend.dto.UserSkillReportResponse;
import com.skilltrack.skilltrack_backend.service.UserSkillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final UserSkillService userSkillService;

    public ReportController(UserSkillService userSkillService){
        this.userSkillService = userSkillService;
    }

    @GetMapping("/skills/{userId}")
    public List<UserSkillReportResponse> getUserSkillReport(
            @PathVariable Long userId
    ){
        return userSkillService.getUserSkillReport(userId);
    }
}
