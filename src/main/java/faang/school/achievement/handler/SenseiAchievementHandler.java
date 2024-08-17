package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.MentorshipStartEvent;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SenseiAchievementHandler extends AbstractAchievementHandler<MentorshipStartEvent>
        implements EventHandler<MentorshipStartEvent> {

    public SenseiAchievementHandler(AchievementService achievementService, AchievementCache achievementCache,
                                    @Value("${listener.type.achievements.sensei}") String achievementTitle,
                                    AchievementProgressRepository achievementProgressRepository) {
        super(achievementService, achievementCache, achievementTitle, achievementProgressRepository);
    }
}
