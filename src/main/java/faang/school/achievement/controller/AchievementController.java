package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/{userId}")
    public List<AchievementDto> getUserAchievements(@PathVariable Long userId){
        return achievementService.getUserAchievements(userId);
    }

    @GetMapping("{id}")
    public AchievementDto getAchievementById(@PathVariable Long id){
        return achievementService.getAchievement(id);
    }

    @GetMapping("/user/{userId}/missed")
    public List<AchievementProgressDto> getMissedUserAchievements(@PathVariable Long userId){
        return achievementService.getUnfinishedAchievements(userId);
    }
}
