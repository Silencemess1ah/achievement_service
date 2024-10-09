package faang.school.achievement.service.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.service.CacheService;
import io.lettuce.core.RedisConnectionException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Retryable(retryFor = RedisConnectionException.class,
        maxAttemptsExpression = "${spring.data.redis.retry.max-attempts}",
        backoff = @Backoff(delayExpression = "${spring.data.redis.retry.max-attempts}"))
public class RedisJsonCacheService<T> implements CacheService<T> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void put(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void put(String key, T value, Duration time) {
        redisTemplate.opsForValue().set(key, value, time);
    }

    @Override
    public T get(String key, Class<T> clazz) {
        Object jsonOrNull = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(jsonOrNull)
                .map(json -> objectMapper.convertValue(json, clazz))
                .orElse(null);
    }

    @Override
    public boolean exists(String key) {
        return Optional.ofNullable(redisTemplate.hasKey(key))
                .orElseThrow(() -> new RedisConnectionException("Redis connection failed"));
    }
}
