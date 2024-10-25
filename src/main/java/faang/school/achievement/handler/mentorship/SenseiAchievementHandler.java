package faang.school.achievement.handler.mentorship;

import faang.school.achievement.config.cache.AchievementCache;
import faang.school.achievement.dto.event.MentorshipStartEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.achievement.AchievementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SenseiAchievementHandler implements EventHandler<MentorshipStartEvent> {

    @Value("${achievements.achievements-title.sensei}")
    private String achievementName;

    @Value("${achievements.achievement-points-needed.sensei}")
    private int achievementPoints;

    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    @Async("executor")
    @Transactional
    @Override
    public void handleEvent(MentorshipStartEvent event) {
        log.info("handleEvent in SenseiAchievementHandler - start");
        Achievement achievement = achievementCache.getAchievement(achievementName);
        boolean hasAchievement = achievementService
                .hasAchievement(event.getMentorId(), achievement.getId());

        if (!hasAchievement) {
            achievementService.createProgressIfNecessary(event.getMentorId(), achievement.getId());

            AchievementProgress progress = achievementService.getProgress(event.getMentorId(), achievement.getId());
            progress.increment();

            if (progress.getCurrentPoints() == achievementPoints) {
                UserAchievement userAchievement = UserAchievement.builder()
                        .achievement(achievement)
                        .userId(event.getMentorId())
                        .build();

                achievementService.giveAchievement(userAchievement);
                log.info("User with id - {} has achieved - {}", event.getMentorId(), achievement.getTitle());
            }
        }

        log.info("handleEvent in SenseiAchievementHandler - finish");
    }
}
