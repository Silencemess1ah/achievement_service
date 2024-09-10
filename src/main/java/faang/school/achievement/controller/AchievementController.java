package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/achievements")
@RequiredArgsConstructor
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping("/all")
    public List<AchievementDto> getAllByFilter(AchievementFilterDto filter) {
        return achievementService.getAllByFilter(filter);
    }

    @GetMapping("/user/{id}")
    public List<UserAchievementDto> getByUserId(@PathVariable Long id) {
        return achievementService.getByUserId(id);
    }

    @GetMapping("/{id}")
    public AchievementDto getById(@PathVariable Long id) {
        return achievementService.getById(id);
    }

    @GetMapping("/user/{id}/progress")
    public List<AchievementProgressDto> getUsersInProgress(@PathVariable Long id) {
        return achievementService.getAchievementInProgress(id);
    }
}
