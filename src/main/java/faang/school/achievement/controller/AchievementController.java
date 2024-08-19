package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievement")
@RequiredArgsConstructor
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping("/filter")
    public List<AchievementDto> getAchievementsWithFilter(@RequestBody AchievementFilterDto achievementFilterDto) {
        return achievementService.getAchievementsWithFilter(achievementFilterDto);
    }

    @GetMapping
    public List<AchievementDto> getAchievements(
            @RequestParam(defaultValue = "${spring.achievement.pagination.page}") int page,
            @RequestParam(defaultValue = "${spring.achievement.pagination.size}") int size) {
        return achievementService.getAllAchievement(page, size);
    }

    @GetMapping("/user/{userId}")
    public List<AchievementDto> getUserAchievements(@PathVariable Long userId) {
        return achievementService.getUserAchievements(userId);
    }

    @GetMapping("/{achievementId}")
    public AchievementDto getAchievementById(@PathVariable Long achievementId) {
        return achievementService.getAchievementById(achievementId);
    }

    @GetMapping("/user/{userId}/process")
    public List<AchievementProgressDto> getProcessAchievementsByUserId(@PathVariable Long userId) {
        return achievementService.getAchievementProgressByUserId(userId);
    }
}
