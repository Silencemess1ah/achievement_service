package faang.school.achievement.handler.mentorship;

import faang.school.achievement.config.achievent.AchievementConfiguration;
import faang.school.achievement.config.cache.AchievementCache;
import faang.school.achievement.dto.achievement.mentorship.MentorshipStartEvent;
import faang.school.achievement.handler.AbstractEventHandler;
import faang.school.achievement.service.achievement.AchievementService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SenseiAchievementHandler extends AbstractEventHandler<MentorshipStartEvent> {

    public SenseiAchievementHandler(AchievementConfiguration achievementConfiguration,
                                      AchievementService achievementService,
                                      AchievementCache achievementCache) {
        super(achievementConfiguration, achievementService, achievementCache);
    }

    @Async("executor")
    @Override
    public void handle(MentorshipStartEvent event) {
        handleAchievement(event, achievementConfiguration.getSensei());
    }

    @Override
    public Class<?> getInstance() {
        return MentorshipStartEvent.class;
    }
}
