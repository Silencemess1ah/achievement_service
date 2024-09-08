package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class AchievementCacheTest {

    @InjectMocks
    private AchievementCache achievementCache;

    @Mock
    private AchievementRepository achievementRepository;
    private Achievement achievement1 = Achievement.builder()
            .id(1L)
            .title("Achievement 1")
            .description("Description 1")
            .build();

    private Achievement achievement2 = Achievement.builder()
            .id(2L)
            .title("Achievement 2")
            .description("Description 2")
            .build();

    private Achievement achievement3 = Achievement.builder()
            .id(3L)
            .title("Achievement 3")
            .description("Description 3")
            .build();
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(achievementRepository.findAll()).thenReturn(Arrays.asList(achievement1, achievement2));

        achievementCache.init();  // Инициализируем кэш вручную для теста
    }

    @Test
    public void testGetAchievement() {
        Achievement achievement = achievementCache.get("Achievement 1");
        assertEquals(achievement1, achievement);
    }

    @Test
    public void testAddToCache() {
        achievementCache.addToCache(achievement3);
        assertEquals(achievement3, achievementCache.get("Achievement 3"));
    }

    @Test
    public void testGetNonExistingAchievement() {
        String wrongTitle = "Non Existing Achievement";
        String message = "Achievement with title " + wrongTitle + " doesn't exist.";

        doThrow(new IllegalArgumentException(message))
                .when(achievementRepository).findByTitle(wrongTitle);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> achievementCache.get(wrongTitle));
        assertEquals(message, e.getMessage());
    }
}
