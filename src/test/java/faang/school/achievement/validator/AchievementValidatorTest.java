package faang.school.achievement.validator;

import faang.school.achievement.exception.DataAchievementValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AchievementValidatorTest {

    @InjectMocks
    private AchievementValidator achievementValidator;

    @Test
    public void testCheckIsEmpty() {
        String name = "";
        assertThrows(DataAchievementValidation.class, () -> achievementValidator.checkIsNullOrEmpty(name));
    }

    @Test
    public void testCheckIsNull() {
        String name = null;
        assertThrows(DataAchievementValidation.class, () -> achievementValidator.checkIsNullOrEmpty(name));
    }
}