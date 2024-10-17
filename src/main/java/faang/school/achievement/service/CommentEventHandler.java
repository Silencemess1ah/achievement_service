package faang.school.achievement.service;

import faang.school.achievement.dto.CommentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.cache.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentEventHandler implements EventHandler<CommentEvent> {

    private final AchievementService achievementService;
    private final AchievementProgressRepository achievementProgressRepository;
    private final CacheService<Achievement> achievementCacheService;

    private final static String ACHIEVEMENT_TITLE = "EXPERT";

    @Override
    public Class<CommentEvent> getEventClass() {
        return CommentEvent.class;
    }

    @Override
    @Async("mainExecutorService")
    @Transactional
    public void handleEvent(CommentEvent event) {
        Achievement achievement = achievementCacheService.getCacheValue(ACHIEVEMENT_TITLE, Achievement.class);
        if (!achievementService.hasAchievement(event.getIdAuthor(), achievement.getId())) {

            achievementService.createProgressIfNecessary(event.getIdAuthor(), achievement.getId());
            AchievementProgress progress = achievementService.getProgress(event.getIdAuthor(), achievement.getId());

            if (progress.getCurrentPoints() <= achievement.getPoints()) {
                progress.increment();
                achievementProgressRepository.save(progress);
            } else if (progress.getCurrentPoints().equals(achievement.getPoints())) {
                achievementService.giveAchievement(event.getIdAuthor(), achievement);
            }
        }
    }
}
