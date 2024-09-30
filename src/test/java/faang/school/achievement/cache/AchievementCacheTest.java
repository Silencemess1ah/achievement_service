package faang.school.achievement.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AchievementCacheTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private AchievementMapper achievementMapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AchievementCache achievementCache;

    private String achievementTitle;
    private String achievementsKey;

    @BeforeEach
    void setUp() {
        achievementTitle = "Test Achievement";
        achievementsKey = "ACHIEVEMENTS";

        when(redisTemplate.opsForHash()).thenReturn(hashOperations);

        try {
            var cacheKeyField = AchievementCache.class.getDeclaredField("ACHIEVEMENT_CACHE_KEY");
            cacheKeyField.setAccessible(true);
            cacheKeyField.set(achievementCache, achievementsKey);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldFillAchievements() {
        Achievement achievement = new Achievement();
        achievement.setTitle(achievementTitle);
        AchievementDto achievementDto = new AchievementDto();
        achievementDto.setTitle(achievementTitle);
        when(achievementRepository.findAll()).thenReturn(List.of(achievement));
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        achievementCache.fillAchievements();

        Map<String, AchievementDto> expectedCacheData = new HashMap<>();
        expectedCacheData.put(achievementTitle, achievementDto);
        verify(achievementRepository, times(1)).findAll();
        verify(achievementMapper, times(1)).toDto(achievement);
        verify(hashOperations, times(1)).putAll(achievementsKey, expectedCacheData);  // Мокаем и проверяем HashOperations
    }

    @Test
    void shouldReturnAchievementByTitleWhenExistsInCache() {
        Object cachedAchievement = new Object();
        AchievementDto expectedAchievementDto = new AchievementDto();
        expectedAchievementDto.setTitle(achievementTitle);
        when(redisTemplate.opsForHash().get(achievementsKey, achievementTitle)).thenReturn(cachedAchievement);
        when(objectMapper.convertValue(cachedAchievement, AchievementDto.class)).thenReturn(expectedAchievementDto);

        AchievementDto result = achievementCache.getByTitle(achievementTitle);

        assertNotNull(result);
        assertEquals(expectedAchievementDto, result);
        verify(objectMapper, times(1)).convertValue(cachedAchievement, AchievementDto.class);
    }

    @Test
    void shouldReturnNullWhenAchievementNotInCache() {
        String title = "Non Existent Achievement";
        when(redisTemplate.opsForHash().get(achievementsKey, title)).thenReturn(null);

        AchievementDto result = achievementCache.getByTitle(title);

        assertNull(result);
        verify(objectMapper, never()).convertValue(any(), eq(AchievementDto.class));
    }
}