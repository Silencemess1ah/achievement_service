package faang.school.achievement.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementCacheTest {

    @Mock
    AchievementRepository achievementRepository;

    @Mock
    RedisTemplate<String, Object> redisTemplate;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    HashOperations<String, Object, Object> hashOperations;

    @InjectMocks
    AchievementCache achievementCache;

    String achievementKey;
    String achievementTitle;
    Achievement achievement;
    String achievementJson;
    List<Achievement> achievements;

    @BeforeEach
    void setUp() {
        achievementTitle = "title";
        achievement = new Achievement();
        achievement.setTitle(achievementTitle);
        achievementJson = achievement.toString();
        achievements = List.of(achievement);

        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    @DisplayName("Should initialize cache with achievements from repository")
    void init() throws JsonProcessingException {
        when(achievementRepository.findAll()).thenReturn(achievements);
        when(objectMapper.writeValueAsString(achievement)).thenReturn(achievementJson);

        achievementCache.init();

        verify(achievementRepository).findAll();
        verify(objectMapper).writeValueAsString(achievement);
        verify(redisTemplate).opsForHash();
        verify(hashOperations).put(achievementKey, achievementTitle, achievementJson);
    }

    @Test
    @DisplayName("Should return Achievement when found in cache by title")
    void getAchievementByTitle() throws JsonProcessingException {
        when(hashOperations.get(achievementKey, achievementTitle)).thenReturn(achievementJson);
        when(objectMapper.readValue(achievementJson, Achievement.class)).thenReturn(achievement);

        Optional<Achievement> result = achievementCache.getAchievementByTitle(achievementTitle);

        verify(redisTemplate).opsForHash();
        verify(hashOperations).get(achievementKey, achievementTitle);
        verify(objectMapper).readValue(achievementJson, Achievement.class);
        assertEquals(Optional.of(achievement), result);
    }
}