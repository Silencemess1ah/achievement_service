package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementCacheTest {

    @InjectMocks
    private AchievementCache cache;

    @Mock
    private AchievementRepository repository;

    private Achievement achievement;
    private Achievement secondAchievement;

    @BeforeEach
    void setUp() {
        achievement = Achievement.builder()
                .title("Title1")
                .description("description1")
                .build();
        secondAchievement = Achievement.builder()
                .title("Title2")
                .description("description2")
                .build();
    }

    @Test
    public void testCacheInit() {
        //given & when
        when(repository.findAll()).thenReturn(List.of(achievement, secondAchievement));
        cache.initCache();

        //then
        assertEquals(2, cache.getAchievements().size());

    }

    @Test
    public void testGetAchievementSuccess() {
        //given & when
        cache.getAchievements().put("Title1", achievement);

        Achievement result = cache.get("Title1");

        //then
        assertEquals(achievement, result);
    }

    @Test
    void testGetNonExistentAchievement() {
        //given & when
        Achievement result = cache.get("Non Existent Achievement");

        //then
        assertNull(result);
    }


    @Test
    void testGetAll() {
        //given & when
        cache.getAchievements().put("Title1", achievement);
        cache.getAchievements().put("Title2", secondAchievement);

        Collection<Achievement> result = cache.getAll();

        // Then
        assertEquals(2, result.size());
    }
}
