package faang.school.achievement.cache;

import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AchievementCacheTest {

    @Mock
    AchievementRepository achievementRepository;

    @InjectMocks
    AchievementCache achievementCache;

    @Test
    public void testFillCacheSuccessful() {

        achievementCache.fillAchievementCache();

        verify(achievementRepository, times(1)).findAll();
    }
}
