package faang.school.achievement.service.eventhandler.profilepic;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.event.ProfilePicEvent;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.eventhandler.AbstractEventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HandsomeAchievementHandler extends AbstractEventHandler<ProfilePicEvent> {

    public HandsomeAchievementHandler(@Value("${achievements.handsome}") String achievementTitle,
                                      AchievementCache achievementCache,
                                      AchievementService achievementService) {

        super(achievementTitle, achievementCache, achievementService);
    }
}
