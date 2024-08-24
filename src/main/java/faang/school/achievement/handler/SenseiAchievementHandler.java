package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.MentorshipStartEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SenseiAchievementHandler extends EventHandler<MentorshipStartEvent> {

    @Value("${handler.achievement.sensei.title}")
    private String senseiAchievementTitle;
    @Value("${handler.achievement.sensei.points}")
    private long pointsToEarnAchievement;

    public SenseiAchievementHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    public String getAchievementTitle() {
        return senseiAchievementTitle;
    }

    @Override
    public long getUserId(MentorshipStartEvent mentorshipStartEvent){
        return mentorshipStartEvent.getReceiverId();
    }

    @Override
    public long getPointsToEarnAchievement(){
        return pointsToEarnAchievement;
    }
}
