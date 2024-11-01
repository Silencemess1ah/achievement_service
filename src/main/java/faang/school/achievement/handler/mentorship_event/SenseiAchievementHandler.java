package faang.school.achievement.handler.mentorship_event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.MentorshipStartEventDto;
import faang.school.achievement.handler.AchievementProgressHandler;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;

public class SenseiAchievementHandler extends MentorshipEventHandler {

    @Value("achievements.mentorship-achievement.sensei.name")
    private String achievement;

    public SenseiAchievementHandler(AchievementService achievementService,
                                    AchievementCache achievementCache,
                                    AchievementProgressHandler achievementProgressHandler) {
        super(achievementService, achievementCache, achievementProgressHandler);
    }

    @Override
    protected String getAchievementTitle() {
        return achievement;
    }

    @Override
    public void handle(MentorshipStartEventDto event) {
        handleAchievement(event.mentorId(), getAchievementTitle());
    }
}
