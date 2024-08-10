package faang.school.achievement.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.UserAchievementEvent;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.UserAchievement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.SerializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AchievementPublisher implements MessagePublisher<UserAchievement> {
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserAchievementMapper userAchievementMapper;
    private final ObjectMapper objectMapper;
    private final ChannelTopic achievementTopic;

    @Override
    public void publish(UserAchievement userAchievement) {
        UserAchievementEvent eventMessage = userAchievementMapper.toEvent(userAchievement);
        try {
            redisTemplate.convertAndSend(achievementTopic.getTopic(), objectMapper.writeValueAsString(eventMessage));
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize message : User Achievement Event");
            throw new SerializationException("Failed to serialize message : User Achievement Event", e);
        }


    }
}
