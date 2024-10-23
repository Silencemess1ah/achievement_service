package faang.school.achievement.listener;

import faang.school.achievement.handler.AbstractEventHandler;
import faang.school.achievement.model.event.PostEvent;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PostEventListenerKafka extends AbstractEventHandler<PostEvent> {
    public PostEventListenerKafka(AchievementCache achievementCache,
                                  AchievementService achievementService,
                                  @Value("${achievement-titles.leader}") String achievementTitle) {
        super(achievementCache, achievementService, achievementTitle);
    }

    @KafkaListener(topics = "${spring.data.kafka.channels.post_channel}", id = "spring.data.kafka.group")
    public void listen(PostEvent event) {
        super.handle(event);
    }
}
