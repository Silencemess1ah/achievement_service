package faang.school.achievement.handler;

import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class AbstractEventHandler<T> implements EventHandler<T>{
    protected final AchievementService achievementService;
    protected final AchievementProgressService achievementProgressService;
    protected final UserAchievementService userAchievementService;

    public abstract String getAchievementName();
}
