package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Evgenii Malkov
 */
@RestController()
@RequestMapping("achievement")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping("{id}")
    public AchievementDto getAchievementById(@PathVariable long id) {
        return achievementService.getAchievementById(id);
    }

    @GetMapping()
    public List<AchievementDto> getAchievements(@ModelAttribute AchievementFilterDto filter) {
        return achievementService.getAchievements(filter);
    }

    @GetMapping("user/{id}")
    public List<UserAchievementDto> getCurrentUserAchievements(@PathVariable long id) {
        return achievementService.getUserAchievements(id);
    }

    @GetMapping("user/{id}/progress")
    public List<AchievementProgressDto> getCurrentUserAchievementsInProgress(@PathVariable long id) {
        return achievementService.getUserAchievementsInProgress(id);
    }
}
