package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
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
    private AchievementCache cache;
    @Mock
    private AchievementRepository repository;

    @Test
    public void testCacheInit() {
        //given
        Achievement achievement1 = Achievement.builder().title("Title1").description("description1").build();
        Achievement achievement2 = Achievement.builder().title("Title2").description("description2").build();
        //when
        when(repository.findAll()).thenReturn(List.of(achievement1, achievement2));
        cache.initCache();
        //then
        assertEquals(2, cache.getAchievements().size());

    }

    @Test
    public void testGetAchievement() {
        //given
        Achievement achievement1 = Achievement.builder().title("Title1").description("description1").build();
        //when
        cache.getAchievements().put("Title1", achievement1);
        //then
        assertEquals(achievement1, cache.get("Title1"));

    }


}
