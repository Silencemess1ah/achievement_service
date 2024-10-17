package faang.school.achievement.service.cache;

import faang.school.achievement.dto.CommentEvent;
import faang.school.achievement.listener.EventDispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CacheServiceImplTest {

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private CacheServiceImpl<CommentEvent> cacheService;

    private CommentEvent commentEvent;
    private String key;

    @BeforeEach
    public void setUp() {
        commentEvent = new CommentEvent();
        commentEvent.setComment("comment");
        commentEvent.setIdComment(1L);
        commentEvent.setIdAuthor(1L);
        commentEvent.setIdPost(1L);

        key = "key";
    }

    @Test
    public void testSetCacheValue() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        cacheService.setCacheValue(key, commentEvent);
        verify(valueOperations, times(1)).set(key, commentEvent);
    }

    @Test
    public void testGetCacheValue() {
        CommentEvent redisValue = commentEvent;

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(key)).thenReturn(redisValue);

        CommentEvent actualCommentEvent = cacheService.getCacheValue(key, CommentEvent.class);

        verify(redisTemplate, times(1)).opsForValue();
        verify(valueOperations, times(1)).get(key);
        verify(objectMapper, times(1)).convertValue(redisValue, CommentEvent.class);
        assertEquals(commentEvent, actualCommentEvent);
    }
}
