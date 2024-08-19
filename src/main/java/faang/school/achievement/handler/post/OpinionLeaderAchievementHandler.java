package faang.school.achievement.handler.post;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.post.PostEvent;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpinionLeaderAchievementHandler extends PostEventHandler {


    public OpinionLeaderAchievementHandler(AchievementCache achievementCache,
                                           AchievementService achievementService,
                                           @Value("${listener.type.achievements.opinion_leader}") String title,
                                           AchievementProgressRepository achievementProgressRepository) {
        super(achievementCache, achievementService, title, achievementProgressRepository);
    }

    @Override
    public void handle(PostEvent postEvent) {
        processEvent(postEvent);
    }
}