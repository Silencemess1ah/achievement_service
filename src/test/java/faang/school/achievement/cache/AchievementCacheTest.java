package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AchievementCacheTest {

    @InjectMocks
    private AchievementCache achievementCache;
    private Achievement achievement1;
    private static final String VALID_TITLE = "Title 1";
    private static final String NOT_VALID_TITLE = "123";

    @BeforeEach
    void setUp() {
        achievement1 = Achievement.builder()
                .title(VALID_TITLE)
                .build();

        achievementCache.getCache().put(achievement1.getTitle(), achievement1);
    }

    @Test
    @DisplayName("When correct title passed return achievement by title")
    public void whenExistingTitlePassedThenReturnAchievement() {
        Achievement resultAchievement = achievementCache.getAchievement(achievement1.getTitle());

        assertEquals(achievement1.getTitle(), resultAchievement.getTitle());
    }

    @Test
    @DisplayName("When not valid title passed then throw exception")
    public void whenNonExistingTitlePassedThenThrowException(){
        assertThrows(RuntimeException.class, () -> achievementCache.getAchievement(NOT_VALID_TITLE));
    }
}
