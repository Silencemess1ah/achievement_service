package faang.school.achievement.controller;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.model.dto.AchievementDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/achievement")
public class AchievementController {

    private final UserContext userContext;
    private final AchievementService achievementService;

    @PostMapping("/{achievementId}")
    public void assignAchievement(@PathVariable long achievementId) {
        long userId = userContext.getUserId();
        log.info("Attempt to assign achievement with id {} to user with id {}", userId, achievementId);
        achievementService.assignAchievement(userId, achievementId);
        log.info("Successfully assigning achievement with id {} to user with id {}", userId, achievementId);
    }

    @GetMapping("/{achievementId}")
    public AchievementDto getAchievement(@PathVariable long achievementId) {
        log.info("Attempt to get achievement with id {}", achievementId);
        AchievementDto achievementDto = achievementService.getAchievement(achievementId);
        log.info("Successfully getting achievement with id {}", achievementId);
        return achievementDto;
    }
}
