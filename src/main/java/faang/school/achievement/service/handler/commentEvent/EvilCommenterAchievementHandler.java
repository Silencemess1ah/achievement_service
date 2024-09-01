package faang.school.achievement.service.handler.commentEvent;

import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.publisher.AchievementPublisher;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EvilCommenterAchievementHandler extends CommentEventHandler {

    @Autowired
    public EvilCommenterAchievementHandler(AchievementCache cache, AchievementService service, AchievementPublisher achievementPublisher,
                                           @Value("${data.achievements.9.name}") String nameAchievement,
                                           AchievementMapper achievementMapper) {
        super(cache, service, achievementPublisher, nameAchievement, achievementMapper);
    }


    @Override
    public boolean checkConditionForInc() {
        return true;
    }
}
