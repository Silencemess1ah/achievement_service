package faang.school.achievement.service.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisConnectionException;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisJsonCacheServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private RedisJsonCacheService<TestValue> cacheService;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    private final String key = "testKey";
    private final TestValue testValue = new TestValue("testValue");

    @Test
    void put_shouldStoreValueInRedis() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        cacheService.put(key, testValue);

        verify(redisTemplate).opsForValue();
        verify(valueOperations).set(key, testValue);
    }

    @Test
    void put_withExpiry_shouldStoreValueInRedisWithExpiry() {
        Duration duration = Duration.ofMinutes(10);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        cacheService.put(key, testValue, duration);

        verify(redisTemplate).opsForValue();
        verify(valueOperations).set(key, testValue, duration);
    }

    @Test
    void get_shouldReturnValueWhenPresent() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(key)).thenReturn(testValue);
        when(objectMapper.convertValue(testValue, TestValue.class)).thenReturn(testValue);

        TestValue result = cacheService.get(key, TestValue.class);

        assertEquals(testValue, result);
    }

    @Test
    void get_shouldReturnNullWhenNotPresent() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(key)).thenReturn(null);

        TestValue result = cacheService.get(key, TestValue.class);

        assertNull(result);
    }

    @Test
    void exists_shouldReturnTrueWhenKeyExists() {
        when(redisTemplate.hasKey(key)).thenReturn(true);

        boolean result = cacheService.exists(key);

        assertTrue(result);
    }

    @Test
    void exists_shouldThrowExceptionWhenRedisConnectionFails() {
        String message = "Redis connection failed";
        when(redisTemplate.hasKey(key)).thenReturn(null);

        RedisConnectionException exception = assertThrows(RedisConnectionException.class, () -> cacheService.exists(key));

        assertEquals(message, exception.getMessage());
    }

    @Data
    private static class TestValue {
        private String value;

        public TestValue(String value) {
            this.value = value;
        }
    }
}
