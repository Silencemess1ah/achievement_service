package faang.school.achievement.service.eventhandler.mentorship;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.event.MentorshipEvent;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.eventhandler.AbstractEventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SenseiAchievementHandler extends AbstractEventHandler<MentorshipEvent> {
    public SenseiAchievementHandler(@Value("${achievements.sensei}") String achievementTitle,
                                    AchievementCache achievementCache,
                                    AchievementService achievementService) {
        super(achievementTitle, achievementCache, achievementService);
    }
}
