package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ExtendWith(MockitoExtension.class)
public class AchievementCacheTest {
    @Mock
    private AchievementRepository achievementRepository;
    private AchievementCache achievementCache;
    private Iterable<Achievement> achievements;
    private List<Achievement> newAchievements;
    private Achievement achievementOne;
    private Achievement achievementTwo;

    @BeforeEach
    void setUp() {
        achievements = Collections.emptySet();
        achievementOne = Achievement.builder()
                .id(1L)
                .title("one")
                .points(10)
                .build();
        achievementTwo = Achievement.builder()
                .id(2L)
                .title("two")
                .points(20)
                .build();
        newAchievements = new ArrayList<>(List.of(achievementOne, achievementTwo));
    }

    @Test
    public void testFillWithEmptyCollection() {
        Mockito.when(achievementRepository.findAll()).thenReturn(achievements);
        achievementCache = new AchievementCache(achievementRepository);
        achievementCache.fill();
        Mockito.verify(achievementRepository, Mockito.times(1)).findAll();
        Assertions.assertEquals(achievementCache.getCache().size(), 0);
    }

    @Test void testFill() {
        Mockito.when(achievementRepository.findAll()).thenReturn(newAchievements);
        achievementCache = new AchievementCache(achievementRepository);
        achievementCache.fill();
        Mockito.verify(achievementRepository, Mockito.times(1)).findAll();
        Assertions.assertEquals(2, achievementCache.getCache().size());
    }
}
