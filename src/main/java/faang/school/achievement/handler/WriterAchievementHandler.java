package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.event.PostEventDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WriterAchievementHandler extends AbstractAchievementHandler<PostEventDto> implements EventHandler<PostEventDto> {

    public WriterAchievementHandler(AchievementService achievementService, AchievementCache achievementCache,
                                    @Value("${handler.achievement.names.writer}") String achievementTitle,
                                    AchievementProgressRepository achievementProgressRepository,
                                    AchievementMapper achievementMapper) {
        super(achievementService, achievementCache, achievementTitle, achievementProgressRepository, achievementMapper);
    }
}
