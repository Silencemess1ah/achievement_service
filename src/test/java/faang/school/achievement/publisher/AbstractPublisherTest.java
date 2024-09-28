package faang.school.achievement.publisher.redis;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

@ExtendWith(MockitoExtension.class)
public class AbstractPublisherTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AbstractPublisher abstractPublisher;

    private final String topic = "testTopic";
    private final Object message = new Object();

    @Test
    @DisplayName("Success")
    void testPublish_successful() throws JsonProcessingException {
        String mockMessage = "{message: serializedMessage}";
        when(objectMapper.writeValueAsString(message)).thenReturn(mockMessage);
        abstractPublisher.publish(topic, message);
        verify(redisTemplate).convertAndSend(topic, mockMessage);
    }

    @Test
    @DisplayName("Failed serialization")
    void testPublish_withException() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("Failed to parse") {});

        assertThrows(RuntimeException.class, () -> abstractPublisher.publish(topic, message));

        verify(objectMapper).writeValueAsString(message);
    }
}