package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.ProfilePicEvent;
import faang.school.achievement.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandsomeAchievementHandler extends AbstractAchievementHandler implements EventHandler<ProfilePicEvent> {

    @Value("${achievement-handler.handsome-achievement-handler.achievement-name}")
    private String achievementTitle;

    public HandsomeAchievementHandler(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @Override
    public void handleEvent(ProfilePicEvent event) {
        processAchievementEvent(achievementTitle, event.getUserId());
    }

    @Override
    public Class<ProfilePicEvent> getType() {
        return ProfilePicEvent.class;
    }
}
