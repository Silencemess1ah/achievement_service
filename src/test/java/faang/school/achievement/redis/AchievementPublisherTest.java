package faang.school.achievement.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.messaging.publisher.AchievementPublisher;
import faang.school.achievement.model.AchievementEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AchievementPublisherTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ChannelTopic achievementChannelTopic;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AchievementPublisher achievementPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(achievementChannelTopic.getTopic()).thenReturn("achievement_channel");
    }

    @Test
    void publishAchievementSuccess() throws Exception {
        AchievementEvent achievementEvent = new AchievementEvent(1L, "Title", "Description", "RARE", 100L);
        String message = "{\"id\":1,\"title\":\"Title\",\"description\":\"Description\",\"rarity\":\"RARE\",\"points\":100}";

        when(objectMapper.writeValueAsString(achievementEvent)).thenReturn(message);

        achievementPublisher.publishAchievement(achievementEvent);

        verify(redisTemplate, times(1)).convertAndSend("achievement_channel", message);
    }

    @Test
    void publishAchievementExceptionHandling() throws Exception {
        AchievementEvent achievementEvent = new AchievementEvent(1L, "Title", "Description", "RARE", 100L);
        when(objectMapper.writeValueAsString(achievementEvent)).thenThrow(new RuntimeException("Serialization error"));

        achievementPublisher.publishAchievement(achievementEvent);

        verify(redisTemplate, never()).convertAndSend(anyString(), anyString());
    }
}