package faang.school.achievement.validator;

import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

@Component
public class AchievementCacheValidator {
    public void validateAchievementNotNull(Achievement achievement, String title) {
        if(achievement == null) {
            throw new IllegalArgumentException("Achievement with title '" + title + "' not found in cache.");
        }
    }
}
