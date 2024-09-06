package faang.school.achievement.validator;

import faang.school.achievement.exception.DataValidationException;
import org.springframework.stereotype.Component;

@Component
public class AchievementValidator {

    public void checkTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new DataValidationException("Achievement title can't be empty");
        }
    }
}