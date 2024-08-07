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
@RequestMapping(value = "/achievement")
@Tag(name = "Контроллер альбома")
public class AchievementController {

    private final AchievementService achievementService;

    @Operation(summary = "Создание достижения",
            description = "Создает новые достижения")
    @PutMapping("/create")
    public AchievementDto createAchievement(AchievementDto achievementDto) {
        if (achievementDto == null) {
            log.error("archievementDto is null");
            throw new IllegalArgumentException("archievementDto is null");
        } else {
            return achievementService.createAchievement(achievementDto);
        }
    }

    @Operation(summary = "Удалить достижение",
            description = "Удаляет достижение по id")
    @PutMapping("/delete")
    public void deleteAchievement(Long achievementId) {
        if (achievementId == null) {
            log.error("achievementId is null");
            throw new IllegalArgumentException("achievementId is null");
        } else {
            achievementService.deleteAchievement(achievementId);
        }
    }

    @Operation(summary = "Добавляет достижение пользователю",
            description = "Добавляет достижение пользователю")
    @PutMapping("/createForUser")
    public UserAchievement createAchievementForUser(Long achievementId) {
        if (achievementId == null) {
            log.error("archievementId is null");
            throw new IllegalArgumentException("archievementId is null");
        } else {
            return achievementService.createAchievementForUser(achievementId);
        }
    }

    @Operation(summary = "Удалить достижение у пользователя",
            description = "Удаление у пользователя достижения")
    @PutMapping("/deleteForUser")
    public void deleteAchievementForUser(Long achievementId) {
        if (achievementId == null) {
            log.error("achievementId is null");
            throw new IllegalArgumentException("achievementId is null");
        } else {
            achievementService.deleteAchievementForUser(achievementId);
        }
    }

    @Operation(summary = "Получить достижения",
            description = "Получить все достижения")
    @GetMapping("/getAllAchievement")
    public List<AchievementDto> getAllAchievement(AchievementFilterDto achievementFilterDto) {
        return achievementService.getAllAchievement(achievementFilterDto);
    }

    @Operation(summary = "Получить достижения пользователя",
            description = "Получить все достижения пользователя по userId")
    @GetMapping("/getAllAchievementForUser/{userId}")
    public Optional<Achievement> getAllAchievementForUser(@PathVariable Long userId) {
        return achievementService.getAllAchievementForUser(userId);
    }

    @Operation(summary = "Получить достижение по id",
            description = "Получить достижение по id")
    @GetMapping("/getAchievement/{achievementId}")
    public Optional<Achievement> getAchievement(@PathVariable Long achievementId) {
        return achievementService.getAchievement(achievementId);
    }

    @Operation(summary = "Получить не полученные достижение по userId",
            description = "Получить не полученные достижение по userId")
    @GetMapping("/getNoAchievement/{userId}")
    public List<Optional<Achievement>> getNoAchievement(@PathVariable Long userId) {
        return achievementService.getNoAchievement(userId);
    }
}