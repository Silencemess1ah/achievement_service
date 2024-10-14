package faang.school.achievement.publisher.achievement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.config.redis.RedisTopicsFactory;
import faang.school.achievement.dto.AchievementEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.Topic;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementPublisherTest {
    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    @Mock
    private RedisTopicsFactory redisTopicsFactory;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private Topic topic;

    @InjectMocks
    private AchievementPublisher achievementPublisher;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(achievementPublisher, "achievementChannel", "achievementChannel");
    }

    @Test
    void publishCommentEvent_shouldPublishAchievementEventToRedis() throws JsonProcessingException {
        AchievementEvent event = new AchievementEvent(
                1L,
                "Str",
                "AchievementTitle",
                "Rarity.Description",
                1,
                LocalDateTime.now(),
                LocalDateTime.now());

        String eventJson = "{" +
                "\"id\":1," +
                "\"title\":\"Str\"," +
                "\"description\":\"AchievementTitle\"," +
                "\"rarity\":\"Description\"," +
                "\"points\":1," +
                "\"createdAt\":\"2024-10-14T23:07:09\"," +
                "\"updatedAt\":\"2024-10-14T23:07:09\"" +
                "}";
        when(objectMapper.writeValueAsString(event)).thenReturn(eventJson);
        when(redisTopicsFactory.getTopic("achievementChannel")).thenReturn(topic);
        when(topic.getTopic()).thenReturn("achievementChannel");

        achievementPublisher.publishCommentEvent(event);

        verify(redisTemplate, times(1)).convertAndSend(topic.getTopic(), eventJson);
    }
}