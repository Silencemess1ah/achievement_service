package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.CommentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ExpertAchievementHandler implements EventHandler<CommentEvent> {

    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    private String expertAchievementTittle;

    @Autowired
    public ExpertAchievementHandler(AchievementCache achievementCache,
                                    AchievementService achievementService,
                                    @Value("${handlers.expert-achievement-handler.title}") String expertAchievementTittle) {
        this.achievementCache = achievementCache;
        this.achievementService = achievementService;
        this.expertAchievementTittle = expertAchievementTittle;
    }

    @Async
    @Override
    public boolean checkHandler(CommentEvent event) {
        return true;
    }

    @Async
    @Override
    public void handle(CommentEvent event) {
        if (expertAchievementTittle == null) {
            throw new IllegalStateException("expertAchievementTittle is not initialized");
        }

        Achievement achievement = achievementCache.get(expertAchievementTittle)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));

        if (!isUserHaveAchievement(event.getCommentAuthorId(), achievement)) {
            achievementService.createProgressIfNecessary(event.getCommentAuthorId(), achievement.getId());
            AchievementProgress achievementProgress = achievementService.getProgress(event.getCommentAuthorId(), achievement.getId());
            achievementProgress.increment();
            if (isProgressPointsEnoughToAcquire(achievement.getPoints(), achievementProgress.getCurrentPoints())) {
                achievementService.giveAchievement(event.getCommentAuthorId(), achievement);
                log.info(String.format("User %d reached the achievement \"%s\"", event.getCommentAuthorId(), expertAchievementTittle));
            }
        }
    }

    private boolean isUserHaveAchievement(Long userId, Achievement achievement) {
        return achievementService.hasAchievement(userId, achievement.getId());
    }

    private boolean isProgressPointsEnoughToAcquire(Long requiredPoints, Long achievementPoints) {
        return achievementPoints >= requiredPoints;
    }
}
