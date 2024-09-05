package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.ProfilePicEvent;
import faang.school.achievement.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandsomeAchievementHandler extends AbstractAchievementHandler<ProfilePicEvent> {

    @Value("${achievement-handler.handsome-achievement-handler.achievement-name}")
    private String achievementTitle;
    @Value("${achievement-handler.handsome-achievement-handler.points}")
    private long pointsToEarnAchievement;

    public HandsomeAchievementHandler(AchievementCache achievementCache,
                                      AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    protected String getAchievementTitle() {
        return achievementTitle;
    }

    @Override
    protected long getUserId(ProfilePicEvent event) {
        return event.getUserId();
    }

    @Override
    protected long getPointsToEarnAchievement() {
        return pointsToEarnAchievement;
    }
}
