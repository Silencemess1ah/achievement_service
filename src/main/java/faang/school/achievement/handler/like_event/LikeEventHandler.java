package faang.school.achievement.handler.like_event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.LikeEventDto;
import faang.school.achievement.handler.AbstractEventHandler;
import faang.school.achievement.handler.AchievementProgressHandler;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.service.AchievementService;

public abstract class LikeEventHandler extends AbstractEventHandler<LikeEventDto> {

    public LikeEventHandler(AchievementService achievementService,
                            AchievementCache achievementCache,
                            AchievementProgressHandler achievementProgressHandler) {
        super(achievementService, achievementCache, achievementProgressHandler);
    }

    @Override
    public void handle(LikeEventDto likeEventDto) {
        handleAchievement(likeEventDto.authorId(), getAchievementTitle());
    }
}
