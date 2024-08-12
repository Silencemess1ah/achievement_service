package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/achievements")
@RequiredArgsConstructor
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping("/all")
    public List<AchievementDto> getUserAchievements(@RequestBody AchievementFilterDto filters) {
        return achievementService.getAchievements(filters);
    }

    @GetMapping("/user_achievements/{user_id}")
    public List<AchievementDto> getUserAchievements(@PathVariable("user_id") Long userId) {
        return achievementService.getUserAchievements(userId);
    }

    @GetMapping("/{achievement_id}")
    public AchievementDto getAchievement(@PathVariable("achievement_id") Long achievementId) {
        return achievementService.getAchievement(achievementId);
    }

    @GetMapping("/achievements_in_progress/{user_id}")
    public List<AchievementProgressDto> getAchievementsProgress(@PathVariable("user_id") Long userId) {
        return achievementService.getAchievementsProgress(userId);
    }
}
