package faang.school.achievement.processor;

import faang.school.achievement.event.AchievementPublisher;
import faang.school.achievement.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CelebrityAchievementProcessor extends BaseAchievementProcessor {

    private static final String CELEBRITY_ACHIEVEMENT = "CELEBRITY";

    public CelebrityAchievementProcessor(AchievementService achievementService,
                                         AchievementPublisher achievementPublisher) {
        super(achievementService, achievementPublisher, CELEBRITY_ACHIEVEMENT);
    }
}
