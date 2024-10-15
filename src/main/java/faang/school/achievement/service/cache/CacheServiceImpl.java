package faang.school.achievement.service.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl<T> implements CacheService<T> {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setCacheValue(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public T getCacheValue(String key, Class<T> clazz) {
        return objectMapper.convertValue(redisTemplate.opsForValue().get(key), clazz);
    }
}
