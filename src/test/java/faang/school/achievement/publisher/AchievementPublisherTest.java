//package faang.school.achievement.publisher;
//
//import faang.school.achievement.model.TopicType;
//import faang.school.achievement.dto.event.AchievementEventDto;
//import faang.school.achievement.publisher.redis.AchievementPublisher;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//
//import java.time.LocalDateTime;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class AchievementPublisherTest {
//
//    @Mock
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Mock
//    private ChannelTopic achievementChannel;
//
//    @InjectMocks
//    private AchievementPublisher achievementPublisher;
//
//    private AchievementEventDto event;
//    private String topic;
//
//    @BeforeEach
//    public void init() {
//        event = AchievementEventDto.builder()
//                .userId(1L)
//                .receivedAt(LocalDateTime.now())
//                .build();
//        topic = TopicType.ACHIEVEMENT_CHANNEL.getChannelType();
//    }
//
//    @Test
//    public void testPublish() {
//        when(achievementChannel.getTopic()).thenReturn(topic);
//
//        achievementPublisher.publish(event);
//
//        verify(redisTemplate, times(1)).convertAndSend(topic, event);
//    }
//}
