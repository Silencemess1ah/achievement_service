package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("achievements/")
@RequiredArgsConstructor
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping
    public List<AchievementDto> getAchievements(@RequestParam(required = false) AchievementFilterDto filters) {
        return achievementService.getAchievements(filters);
    }
}
