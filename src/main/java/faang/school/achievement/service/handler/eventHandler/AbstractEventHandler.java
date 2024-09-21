package faang.school.achievement.service.handler.eventHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import faang.school.achievement.dto.event.AchievementEvent;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.event.EventInt;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.publisher.AchievementPublisher;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public abstract class AbstractEventHandler<T extends EventInt> {
    protected final AchievementCache cache;
    protected final AchievementService service;
    private final AchievementPublisher publisher;
    private final String nameAchievement;
    private final AchievementMapper mapper;

    @Async
    @Transactional
    public void process(T event) throws JsonProcessingException {
        Achievement achievement = cache.get(nameAchievement);
        Long achievementId = achievement.getId();
        Long userId = event.getUserId();

        if (service.hasAchievement(userId, achievementId)) {
            return;
        }
        service.createProgressIfNecessary(userId, achievementId);

        if(checkConditionForInc(event)) {
            AchievementProgressDto achievementProgressDto = service.getProgress(userId, achievementId);
            AchievementProgress progress = mapper.toAchievementProgress(achievementProgressDto);
            progress.increment();
            checkAchievementsPoints(achievement, progress, userId);
        }
    }

    public abstract boolean checkConditionForInc(EventInt event);

    private void checkAchievementsPoints(Achievement achievement, AchievementProgress achievementProgress, Long userId) throws JsonProcessingException {
        if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
            delAndGiveAchievement(achievement, achievementProgress, userId);
            publisher.publish(new AchievementEvent(userId, "пользователь получил достижение: %s".formatted(achievement)));
            return;
        }
        service.saveProgress(achievementProgress);
    }

    private void delAndGiveAchievement(Achievement achievement, AchievementProgress achievementProgress, Long userId) {
        service.deleteAchievementProgress(achievementProgress.getId());
        service.giveAchievement(userId, achievement);
    }
}
