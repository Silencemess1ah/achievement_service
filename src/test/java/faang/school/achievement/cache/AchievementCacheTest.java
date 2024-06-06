package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.validator.AchievementValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementCacheTest {

    @InjectMocks
    private AchievementCache achievementCache;

    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private AchievementValidator achievementValidator;

    @Test
    public void testInit() {
        Achievement achievement1 = Achievement.builder().title("Title1").build();
        Achievement achievement2 = Achievement.builder().title("Title2").build();

        when(achievementRepository.findAll()).thenReturn(List.of(achievement1, achievement2));

        achievementCache.init();

        assertEquals(2, achievementCache.getAchievements().size());
        assertEquals(achievement1, achievementCache.getAchievements().get("Title1"));
        assertEquals(achievement2, achievementCache.getAchievements().get("Title2"));
    }

    @Test
    public void testGetAchievement() {
        Achievement achievement3 = Achievement.builder().title("Title3").build();
        achievementCache.getAchievements().put("Title3", achievement3);

        assertEquals(achievement3, achievementCache.getAchievement("Title3"));
    }
}