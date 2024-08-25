package faang.school.achievement.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.AchievementEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementEventPublisherTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ChannelTopic achievementChannel;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @InjectMocks
    private AchievementEventPublisher achievementEventPublisher;

    private String achievementEventJson;
    private String channelName;
    private AchievementEvent achievementEvent;

    @BeforeEach
    public void setUp(){
        achievementEvent = new AchievementEvent();
        achievementEventJson = "achievementEventJson";
        channelName = "channelName";
    }

    @Test
    @DisplayName("testing publish method")
    void publish() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(achievementEvent)).thenReturn(achievementEventJson);
        when(achievementChannel.getTopic()).thenReturn(channelName);
        achievementEventPublisher.publish(achievementEvent);
        verify(objectMapper, times(1)).writeValueAsString(achievementEvent);
        verify(redisTemplate, times(1))
                .convertAndSend(achievementChannel.getTopic(), achievementEventJson);
    }
}