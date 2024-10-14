package faang.school.achievement.publisher.achievement;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.config.redis.RedisTopicsFactory;
import faang.school.achievement.dto.AchievementEvent;
import faang.school.achievement.publisher.AbstractEventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

@Component
public class AchievementPublisher extends AbstractEventPublisher<AchievementEvent> {
    @Value("${spring.data.redis.channel.achievement}")
    private String achievementChannel;
    private final RedisTopicsFactory redisTopicsFactory;

    public AchievementPublisher(RedisTemplate<String, Object> redisTemplate,
                                      ObjectMapper objectMapper,
                                      RedisTopicsFactory redisTopicsFactory) {
        super(redisTemplate, objectMapper);
        this.redisTopicsFactory = redisTopicsFactory;
    }

    // TODO: 14.10.2024 Использовать этот метод для отправки события в Редис после сохранения достижения в БД
    public void publishCommentEvent(AchievementEvent event) {
        Topic commentTopic = redisTopicsFactory.getTopic(achievementChannel);
        publish(event, commentTopic);
    }
}
