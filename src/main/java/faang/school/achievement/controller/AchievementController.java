package faang.school.achievement.controller;

import faang.school.achievement.AchievementServiceApp;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
    public AchievementDto createAchievementForUser(Long achievementId) {
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
            achievementService.deleteAchievementUserFor(achievementId);
        }
    }
}
