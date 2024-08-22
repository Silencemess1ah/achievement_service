package faang.school.achievement.messaging.handler.profilepic;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.Event;
import faang.school.achievement.messaging.handler.AbstractEventHandler;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HandsomeAchievementHandler extends AbstractEventHandler {
    public HandsomeAchievementHandler(AchievementCache achievementCache,
                                      AchievementService achievementService,
                                      @Value("${listener.type.achievements.handsome}") String title,
                                      AchievementProgressRepository achievementProgressRepository) {
        super(achievementCache, achievementService, title, achievementProgressRepository);
    }

    @Override
    public void handle(Event event) {
        processEvent(event);
    }
}
