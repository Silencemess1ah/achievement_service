package faang.school.achievement.event.handler.mentorship;

import faang.school.achievement.dto.mentorship.MentorshipStartEvent;
import faang.school.achievement.event.handler.AbstractEventHandler;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.achievement.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SenseyAchievementHandler extends AbstractEventHandler<MentorshipStartEvent> {

    @Value("${achievements.title.sensey}")
    private String achievementName;

    public SenseyAchievementHandler(AchievementService achievementService,
                                    UserAchievementRepository userAchievementRepository,
                                    AchievementProgressRepository achievementProgressRepository) {
        super(achievementService, userAchievementRepository, achievementProgressRepository);
    }

    @Override
    protected String getAchievementName() {
        return achievementName;
    }
}
