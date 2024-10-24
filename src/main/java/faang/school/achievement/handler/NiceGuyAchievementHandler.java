package faang.school.achievement.handler;

import faang.school.achievement.event.NiceGuyEvent;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NiceGuyAchievementHandler extends AbstractEventHandler<NiceGuyEvent>{
    public NiceGuyAchievementHandler(AchievementCache achievementCache, AchievementService achievementService,@Value("${achievement-titles.nice-guy}") String achievementTitle) {
        super(achievementCache, achievementService, achievementTitle);
    }
}
