package faang.school.achievement.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.exception.JsonParseException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Component
@RequiredArgsConstructor
public class AchievementCache {
    private final AchievementRepository achievementRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.data.redis.cache.achievement-key}")
    private String achievementKey;

    @PostConstruct
    protected void init() {
        List<Achievement> achievements = achievementRepository.findAll();
        achievements.forEach(this::cacheAchievement);
    }

    public Optional<Achievement> getAchievementByTitle(String title) {
        String json = (String) redisTemplate.opsForHash().get(achievementKey, title);
        return json != null ? Optional.of(deserialize(json)) : Optional.empty();
    }

    private void cacheAchievement(Achievement achievement) {
        String json = serialize(achievement);
        redisTemplate.opsForHash().put(achievementKey, achievement.getTitle(), json);
    }

    private String serialize(Achievement achievement) {
        return handleJsonConversion(() -> objectMapper.writeValueAsString(achievement));
    }

    private Achievement deserialize(String json) {
        return handleJsonConversion(() -> objectMapper.readValue(json, Achievement.class));
    }

    private <T> T handleJsonConversion(Callable<T> callable) {
        try {
            return callable.call();
        } catch (JsonProcessingException e) {
            throw new JsonParseException("Error processing JSON with error: %s".formatted(e.getMessage()));
        } catch (Exception e) {
            throw new RuntimeException(("Unexpected error during JSON conversion with error: %s".formatted(e.getMessage())), e);
        }
    }
}
