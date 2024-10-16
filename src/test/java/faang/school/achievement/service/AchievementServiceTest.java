package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    @InjectMocks
    private AchievementService achievementService;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private AchievementCache achievementCache;
    private Achievement achievement1;
    private Achievement achievement2;
    private List<Achievement> achievements;

    @BeforeEach
    void setUp() {
        achievement1 = Achievement.builder()
                .title("abc")
                .build();
        achievement2 = Achievement.builder()
                .title("def")
                .build();
        achievements = Arrays.asList(achievement1, achievement2);
    }

    @Test
    @DisplayName("Init cache of 2 achievements given by DB")
    public void whenAppStartsThenCallInitMethodAndFillUpCacheFromDB() {
        when(achievementRepository.findAll()).thenReturn(achievements);
        achievementService.initCache();

        verify(achievementCache, Mockito.times(2)).getCache();
    }
}
