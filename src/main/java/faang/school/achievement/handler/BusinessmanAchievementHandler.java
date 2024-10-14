package faang.school.achievement.handler;

import faang.school.achievement.dto.ProjectEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class BusinessmanAchievementHandler implements AchievementHandler {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementService achievementService;

    @Override
    @Async("taskExecutor")
    public void handleAchievement(ProjectEvent event) {
        log.info("Handling achievement for event: {}", event);
        Achievement businessman = achievementRepository.findByTitle("Businessman");
        if (hasAchievement(event, businessman)) {
            achievementProgressRepository.createProgressIfNecessary(event.getAuthorId(), businessman.getId());
        }
        AchievementProgress achievementProgress = achievementService.getProgress(event.getAuthorId(), businessman.getId());
        achievementProgress.setCurrentPoints(achievementProgress.getCurrentPoints() + 1);
        achievementProgressRepository.save(achievementProgress);
        if (achievementProgress.getCurrentPoints() > businessman.getPoints()) {
            achievementService.giveAchievement(event.getAuthorId(), businessman);
        }
    }

    private boolean hasAchievement(ProjectEvent event, Achievement businessman) {
        return userAchievementRepository.existsByUserIdAndAchievementId(event.getAuthorId(), businessman.getId());
    }
}

