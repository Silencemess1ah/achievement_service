package faang.school.achievement.cache;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.validator.AchievementValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AchievementCacheTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOps;

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private AchievementMapper achievementMapper;

    @Mock
    private AchievementValidator achievementValidator;

    @Mock
    private ChannelTopic redisChannelTopic;

    @InjectMocks
    private AchievementCache achievementCache;

    private final String achievementCacheKey = "achievement-cache-key";
    private final int cacheTTL = 24;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        // Установить значения через рефлексию
        try {
            var cacheKeyField = AchievementCache.class.getDeclaredField("achievementCacheKey");
            cacheKeyField.setAccessible(true);
            cacheKeyField.set(achievementCache, achievementCacheKey);

            var cacheTTLField = AchievementCache.class.getDeclaredField("cacheTTL");
            cacheTTLField.setAccessible(true);
            cacheTTLField.set(achievementCache, cacheTTL);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    @DisplayName("Test init() sends init message to Redis channel")
//    void testInit() {
//        achievementCache.init();
//        verify(redisTemplate).convertAndSend(redisChannelTopic.getTopic(), "init");
//    }

    @Test
    @DisplayName("Test getAchievementFromCache when cache hit")
    void testGetAchievementFromCache_CacheHit() {
        String title = "Test Achievement";
        AchievementDto dto = new AchievementDto();

        String buildCacheKey = achievementCacheKey + ":" + title;

        when(valueOps.get(buildCacheKey)).thenReturn(dto);

        AchievementDto result = achievementCache.getByTitle(title);

        verify(valueOps, never()).set(anyString(), any(), anyLong(), any());
        assertEquals(dto, result);
    }

//    @Test
//    @DisplayName("Test getAchievementFromCache when cache miss and updates cache")
//    void testGetAchievementFromCache_CacheMiss() {
//        String title = "Test Achievement";
//        Achievement achievement = new Achievement();
//        AchievementDto dto = new AchievementDto();
//
//        String buildCacheKey = achievementCacheKey + ":" + title;
//
//        when(valueOps.get(buildCacheKey)).thenReturn(null);
//        when(achievementRepository.findB(title)).thenReturn(Optional.of(achievement));
//        when(achievementMapper.toDto(achievement)).thenReturn(dto);
//
//        AchievementDto result = achievementCache.getAchievementFromCache(title);
//
//        verify(valueOps).set(buildCacheKey, dto, cacheTTL, TimeUnit.HOURS);
//        assertEquals(dto, result);
//    }

//    @Test
//    @DisplayName("Test getAchievementFromCache throws exception when achievement not found in DB")
//    void testGetAchievementFromCache_ThrowsEntityNotFoundException() {
//        String title = "Nonexistent Achievement";
//        String buildCacheKey = achievementCacheKey + ":" + title;
//
//        when(valueOps.get(buildCacheKey)).thenReturn(null);
//        when(achievementRepository.findByTitle(title)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> achievementCache.getAchievementFromCache(title));
//    }
//
//    @Test
//    @DisplayName("Test updateAchievementInCache updates cache with new DTO")
//    void testUpdateAchievementInCache() {
//        String title = "Test Achievement";
//        Achievement achievement = new Achievement();
//        AchievementDto dto = new AchievementDto();
//
//        when(achievementRepository.findByTitle(title)).thenReturn(Optional.of(achievement));
//        when(achievementMapper.toDto(achievement)).thenReturn(dto);
//
//        AchievementDto result = achievementCache.updateAchievementInCache(title);
//
//        assertEquals(dto, result);
//    }
}