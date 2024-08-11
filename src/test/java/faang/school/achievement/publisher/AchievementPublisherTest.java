package faang.school.achievement.publisher;

import faang.school.achievement.dto.AchievementEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AchievementPublisherTest {

    private static final String CHANNEL_NAME = "achievement_channel";
    private static final String JSON_STRING = "{\"id\":1,\"title\":\"Title\"}";
    private static final long ID = 1L;
    private static final String TITLE = "Title";
    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ChannelTopic achievementChannelTopic;

    @InjectMocks
    private AchievementPublisher achievementPublisher;

    @BeforeEach
    void setUp() {
        Mockito.when(achievementChannelTopic.getTopic()).thenReturn(CHANNEL_NAME);
    }

    @Test
    void testValidPublishAchievement() throws Exception {
        //Arrange
        AchievementEvent achievementEvent = new AchievementEvent(ID, TITLE);
        //Act
        achievementPublisher.publish(achievementEvent);
        //Assert
        Mockito.verify(redisTemplate, times(1)).convertAndSend(CHANNEL_NAME, JSON_STRING);
    }
}