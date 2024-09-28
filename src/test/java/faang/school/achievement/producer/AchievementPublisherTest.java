package faang.school.achievement.producer;

import faang.school.achievement.dto.PublishedUserAchievementDto;
import faang.school.achievement.mapper.PublishedUserAchievementMapper;
import faang.school.achievement.model.UserAchievement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AchievementPublisherTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ChannelTopic channelTopic;

    @Mock
    private PublishedUserAchievementMapper publishedUserAchievementMapper;

    @InjectMocks
    private AchievementPublisher achievementPublisher;

    @Test
    void testPublish() {
        // Arrange
        UserAchievement userAchievement = new UserAchievement();
        PublishedUserAchievementDto publishedUserAchievementDto = new PublishedUserAchievementDto();

        when(publishedUserAchievementMapper.toDto(any(UserAchievement.class)))
                .thenReturn(publishedUserAchievementDto);
        when(channelTopic.getTopic()).thenReturn("testChannel");

        // Act
        achievementPublisher.publish(userAchievement);

        // Assert
        verify(publishedUserAchievementMapper, times(1)).toDto(eq(userAchievement));
        verify(redisTemplate, times(1)).convertAndSend(eq("testChannel"), eq(publishedUserAchievementDto));
    }
}