package faang.school.achievement.integration.listener;

import faang.school.achievement.dto.SkillAcquiredEvent;
import faang.school.achievement.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.Topic;
import org.springframework.test.annotation.DirtiesContext;

public class SkillEventListenerIntegrationTest extends IntegrationTestBase {

    private static final long ACTOR_ID = 1L;
    private static final long RECEIVER_ID = 2L;
    private static final long SKILL_ID = 3L;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @DirtiesContext
    @Test
    public void checkSkillAchievement() {
        Topic topic = () -> "skill_channel";
        SkillAcquiredEvent eventMessage = new SkillAcquiredEvent(ACTOR_ID, RECEIVER_ID, SKILL_ID);
        redisTemplate.convertAndSend(topic.getTopic(), eventMessage);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
