package faang.school.achievement.service;

import faang.school.achievement.exception.NotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AchievementCacheTest {

    @Mock
    AchievementRepository achievementRepository;
    @InjectMocks
    AchievementCache achievementCache;

    private Map<String, Achievement> achievementMap;
    String collectorTitle, mrProductivityTitle, expertTitle;
    Achievement collector, mrProductivity, expert;

    @BeforeEach
    void setUp() {
        collectorTitle = "COLLECTOR";
        mrProductivityTitle = "MR PRODUCTIVITY";
        expertTitle = "EXPERT";
        collector = Achievement.builder().title(collectorTitle).build();
        mrProductivity = Achievement.builder().title(mrProductivityTitle).build();
        expert = Achievement.builder().title(expertTitle).build();
        List<Achievement> achievementList = List.of(collector, mrProductivity, expert);
        achievementMap = Map.of(
                collectorTitle, collector,
                mrProductivityTitle, mrProductivity,
                expertTitle, expert
        );

        when(achievementRepository.findAll()).thenReturn(achievementList);
        achievementCache.load();
    }

    @Test
    void testLoad() {
        verify(achievementRepository, times(1)).findAll();
        assertEquals(achievementMap, achievementCache.getAchievements());
    }

    @Test
    void testThrowExceptionWhenAchievementNotExists() {
        assertThrows(NotFoundException.class, () -> achievementCache.get("title"));
    }

    @Test
    void testGet() {
        assertEquals(collector, achievementCache.get(collectorTitle));
        assertEquals(mrProductivity, achievementCache.get(mrProductivityTitle));
        assertEquals(expert, achievementCache.get(expertTitle));
    }
}