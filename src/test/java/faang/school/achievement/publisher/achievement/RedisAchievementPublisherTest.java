package faang.school.achievement.publisher.achievement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AchievementEventDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisAchievementPublisherTest {

    @Mock
    RedisTemplate<String, Object> redisTemplate;

    @Mock
    ChannelTopic achievementTopic;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    private RedisAchievementPublisher redisAchievementPublisher;

    AchievementEventDto achievementEventDto;
    String stringEvent;
    String achievementTopicString;

    @BeforeEach
    void setUp() {
        achievementEventDto = AchievementEventDto.builder().build();
        stringEvent = achievementEventDto.toString();
        achievementTopicString = "achievementTopic";
    }

    @Test
    void publish() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(achievementEventDto)).thenReturn(stringEvent);
        when(achievementTopic.getTopic()).thenReturn(achievementTopicString);

        redisAchievementPublisher.publish(achievementEventDto);

        verify(objectMapper).writeValueAsString(achievementEventDto);
        verify(redisTemplate).convertAndSend(achievementTopicString, stringEvent);
    }
}