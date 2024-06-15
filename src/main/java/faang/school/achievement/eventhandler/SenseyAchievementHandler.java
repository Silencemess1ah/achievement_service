package faang.school.achievement.eventhandler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.MentorshipStartEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SenseyAchievementHandler extends AbstractEventHandler<MentorshipStartEvent> {

    @Value("${achievements.title.sensei}")
    private String achievementName;

    public SenseyAchievementHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    protected String getAchievementName() {
        return achievementName;
    }
}
