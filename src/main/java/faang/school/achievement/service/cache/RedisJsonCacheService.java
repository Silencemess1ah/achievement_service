package faang.school.achievement.service.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisJsonCacheService<T> implements CacheService<T> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void put(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public T get(String key, Class<T> clazz) {
        Object json = redisTemplate.opsForValue().get(key);
        if (json == null) {
            return null;
        }
        try {
            return objectMapper.convertValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }

    @Override
    public Boolean existsBy(String key) {
        return redisTemplate.hasKey(key);
    }
}
