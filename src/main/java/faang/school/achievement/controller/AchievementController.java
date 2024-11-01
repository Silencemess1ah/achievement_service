package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievements")
public class AchievementController {
    private final AchievementService achievementService;
    private final AchievementMapper achievementMapper;
    private final UserAchievementMapper userAchievementMapper;
    private final AchievementProgressMapper achievementProgressMapper;

    @PostMapping("/filters")
    public List<AchievementDto> getAchievementsByFilter(@RequestBody @NonNull AchievementFilterDto filterDto) {
        List<Achievement> achievements = achievementService.getAchievementByFilters(filterDto);
        return achievementMapper.toDtoList(achievements);
    }

    @GetMapping("/{id}")
    public AchievementDto getAchievementById(@PathVariable long id) {
        Achievement achievement = achievementService.getAchievementById(id);
        return achievementMapper.toDto(achievement);
    }

    @GetMapping("/achieved/user")
    public List<UserAchievementDto> getUserAchievements(@RequestParam long id) {
        List<UserAchievement> achievements = achievementService.getUserAchievements(id);
        return userAchievementMapper.toDtoList(achievements);
    }

    @GetMapping("/progress/user")
    public List<AchievementProgressDto> getUserProgress(@RequestParam long id) {
        List<AchievementProgress> achievementsInProgress = achievementService.getUserProgress(id);
        return achievementProgressMapper.toDtoList(achievementsInProgress);
    }
}
