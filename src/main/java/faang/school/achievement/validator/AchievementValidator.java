package faang.school.achievement.validator;

import faang.school.achievement.exception.DataAchievementValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AchievementValidator {
    public void checkIsNullOrEmpty(String name) {
        if (name == null || name.isEmpty()) {
            log.error("Аргумент " + name + " не может быть пустой или null");
            throw new DataAchievementValidation("Аргумент " + name + " не может быть пустой или null");
        }
    }
}