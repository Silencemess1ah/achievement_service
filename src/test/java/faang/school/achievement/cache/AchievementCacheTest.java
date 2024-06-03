package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AchievementCacheTest {
    private static final String ACHIEVEMENT_TITLE = "title_1";

    @Mock
    private AchievementRepository achievementRepository;

    @InjectMocks
    private AchievementCache achievementCache;

    private Achievement achievement;

    @BeforeEach
    void setUp() {
        achievement = Achievement.builder()
                .title(ACHIEVEMENT_TITLE)
                .description("desc_1")
                .rarity(Rarity.EPIC)
                .points(15L)
                .build();
    }

    @Test
    public void whenGetAndAchievementExistsThenGetOptionalOfAchievement() {
        when(achievementRepository.findAll()).thenReturn(List.of(achievement));
        achievementCache.init();
        Optional<Achievement> actual = achievementCache.get(ACHIEVEMENT_TITLE);
        assertThat(actual).isEqualTo(Optional.of(achievement));
    }

    @Test
    public void whenGetAndAchievementExistsThenGetEmptyOptional() {
        when(achievementRepository.findAll()).thenReturn(List.of(achievement));
        achievementCache.init();
        Optional<Achievement> actual = achievementCache.get("Best Igor");
        assertThat(actual).isEmpty();
    }
}