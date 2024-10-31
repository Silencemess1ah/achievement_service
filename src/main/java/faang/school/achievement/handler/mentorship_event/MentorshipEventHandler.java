package faang.school.achievement.handler.mentorship_event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.MentorshipStartEventDto;
import faang.school.achievement.handler.AbstractEventHandler;
import faang.school.achievement.handler.AchievementProgressHandler;
import faang.school.achievement.service.AchievementService;

public abstract class MentorshipEventHandler extends AbstractEventHandler<MentorshipStartEventDto> {

    public MentorshipEventHandler(AchievementService achievementService,
                                  AchievementCache achievementCache,
                                  AchievementProgressHandler achievementProgressHandler) {
        super(achievementService, achievementCache, achievementProgressHandler);
    }
}
