package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping
    public AchievementDto getAchievement(@RequestBody String title) {
        return achievementService.getAchievementFromCache(title);
    }
}
