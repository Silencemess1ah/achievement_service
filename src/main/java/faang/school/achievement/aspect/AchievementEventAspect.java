package faang.school.achievement.aspect;

import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publish.publisher.AchievementEventPublisher;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AchievementEventAspect {

    private final AchievementEventPublisher achievementEventPublisher;

    @AfterReturning(value = "@annotation(faang.school.achievement.annotation.PublishAchievementEvent)", returning = "userAchievement")
    public void publishAchievementEvent(UserAchievement userAchievement) {
        achievementEventPublisher.publishNewUserAchievementEventToBroker(userAchievement);
    }
}