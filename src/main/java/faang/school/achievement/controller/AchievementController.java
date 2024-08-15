package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/achievement")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<AchievementDto> getAllAchievements(@RequestBody AchievementFilterDto achievementFilterDto) {
        return achievementService.getAllAchievements(achievementFilterDto);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AchievementDto> getUserAchievements(@PathVariable Long userId) {
        return achievementService.getAchievementsByUserId(userId);
    }

    @GetMapping("/{achievementId}")
    @ResponseStatus(HttpStatus.OK)
    public AchievementDto getAchievement(@PathVariable Long achievementId) {
        return achievementService.getAchievementById(achievementId);
    }

    @GetMapping("/user/not-attained/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AchievementProgressDto> getUserNotAttainedAchievements(@PathVariable Long userId) {
        return achievementService.getUserNotAttainedAchievements(userId);
    }
}
