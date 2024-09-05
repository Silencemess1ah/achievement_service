package faang.school.achievement.service.eventHandler;

import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@NoArgsConstructor
public class BloggerHandler extends FollowerEventHandler {
    private final String ACHIEVEMENT_TITLE = "BLOGGER";

    public BloggerHandler(AchievementCache cache, AchievementService achievementService, AchievementMapper mapper) {
        super(cache, achievementService, mapper);
    }

    @Override
    @Async
    @Transactional
    public void handle(FollowerEvent event) {
        process(event, ACHIEVEMENT_TITLE);
    }
}
