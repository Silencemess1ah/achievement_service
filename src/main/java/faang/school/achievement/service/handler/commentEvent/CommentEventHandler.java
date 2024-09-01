package faang.school.achievement.service.handler.commentEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import faang.school.achievement.dto.AchievementEvent;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.event.CommentEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.publisher.AchievementPublisher;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

@Data
@RequiredArgsConstructor
public abstract class CommentEventHandler implements EventHandler<CommentEvent> {
    protected final AchievementCache cache;
    protected final AchievementService service;
    private final AchievementPublisher achievementPublisher;
    private final String nameAchievement;
    private final AchievementMapper mapper;

    @Override
    @Async
    @Transactional
    public void process(CommentEvent commentEvent) throws JsonProcessingException {
        Achievement achievement = cache.get(nameAchievement);
        Long achievementId = achievement.getId();
        Long userId = commentEvent.getAuthorId();

        if (service.hasAchievement(userId, achievementId)) {
            return;
        }
        service.createProgressIfNecessary(userId, achievementId);
        if(checkConditionForInc()) {
            AchievementProgressDto achievementProgressDto = service.getProgress(userId, achievementId);
            AchievementProgress progress = mapper.toAchievementProgress(achievementProgressDto);
            progress.increment();
            checkAchievementsPoints(achievement, progress, userId);
        }
    }

    public abstract boolean checkConditionForInc();

    private void checkAchievementsPoints(Achievement achievement, AchievementProgress achievementProgress, Long userId) throws JsonProcessingException {
        if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
            delAndGiveAchievement(achievement, achievementProgress, userId);
            achievementPublisher.publish(new AchievementEvent(userId, "пользователь получил достижение: %s".formatted(achievement)));
            return;
        }
        service.saveProgress(achievementProgress);
    }

    private void delAndGiveAchievement(Achievement achievement, AchievementProgress achievementProgress, Long userId) {
        service.deleteAchievementProgress(achievementProgress.getId());
        service.giveAchievement(userId, achievement);
    }
}
