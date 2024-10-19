package faang.school.achievement.handler;

import faang.school.achievement.dto.PostCreatedEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementType;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

import static faang.school.achievement.model.AchievementType.POST;

@Component
public class WriterHandler extends PostEventHandler {
    private static final AchievementType ACHIEVEMENT_TYPE = POST;

    public WriterHandler(AchievementService achievementService) {
        super(achievementService);
    }

    @Override
    public void handleEvent(PostCreatedEvent event) {
        long userId = event.getAuthorId();
        List<Achievement> achievements = getAchievementsByType(ACHIEVEMENT_TYPE).stream()
                .sorted(Comparator.comparing(Achievement::getRarity))
                .toList();
        for (Achievement achievement : achievements) {
            boolean userHasAchievement = processAchievement(userId, achievement);
            if (!userHasAchievement) {
                return;
            }
        }
    }
}
