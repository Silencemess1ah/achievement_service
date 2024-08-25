package faang.school.achievement.service.handler.eventHandlerImpl.commentEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import faang.school.achievement.dto.AchievementEvent;
import faang.school.achievement.dto.event.CommentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.publisher.AchievementPublisher;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.handler.eventHandlerImpl.CommentEventHandler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EvilCommenterAchievementHandler extends CommentEventHandler {
    private final AchievementPublisher achievementPublisher;
    private String nameAchievement;
    private int points;

    @Autowired
    public EvilCommenterAchievementHandler(AchievementCache cache, AchievementService service, AchievementPublisher achievementPublisher,
                                           @Value("${data.achievements.9.name}") String nameAchievement,
                                           @Value("${data.achievements.9.points}") int points) {
        super(cache, service);
        this.nameAchievement = nameAchievement;
        this.points = points;
        this.achievementPublisher = achievementPublisher;
    }

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

        AchievementProgress progressAchievement = service.getProgress(userId, achievementId);
        progressAchievement.increment();
        checkAchievementsPoints(achievement, progressAchievement, userId);
    }

    private void checkAchievementsPoints(Achievement achievement, AchievementProgress achievementProgress, Long userId) throws JsonProcessingException {
        long currentPoints = achievementProgress.getCurrentPoints();
        if (currentPoints >= points) {
            delAndGiveAchievement(achievement, achievementProgress, userId);
            // какая-то логика оповещения о получении достижения
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
