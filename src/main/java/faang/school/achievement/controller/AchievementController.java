package faang.school.achievement.controller;

import faang.school.achievement.AchievementServiceApp;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/achievements")
@Tag(name = "Контроллер альбома")
public class AchievementController {

    private final AchievementService achievementService;

    @Operation(summary = "Получить достижения",
            description = "Получить все достижения")
    @PostMapping("/filter")
    public List<AchievementDto> getAllAchievement(@RequestBody AchievementFilterDto achievementFilterDto) {
        return achievementService.getAllAchievement(achievementFilterDto);
    }

    @Operation(summary = "Получить достижения пользователя",
            description = "Получить все достижения пользователя по userId")
    @GetMapping("/user/{userId}")
    public List<AchievementDto> getAllAchievementForUser(@PathVariable Long userId) {
        return achievementService.getAllAchievementForUser(userId);
    }

    @Operation(summary = "Получить достижение по id",
            description = "Получить достижение по id")
    @GetMapping("/achievement/{achievementId}")
    public List<AchievementDto> getAchievement(@PathVariable Long achievementId) {
        return achievementService.getAchievement(achievementId);
    }

    @Operation(summary = "Получить не полученные достижение по userId",
            description = "Получить не полученные достижение по userId")
    @GetMapping("/progress/user/{userId}")
    public List<AchievementDto> getNoAchievement(@PathVariable Long userId) {
        return achievementService.getNoAchievement(userId);
    }
}