package faang.school.achievement.listener;

import faang.school.achievement.handler.AbstractEventHandler;
import faang.school.achievement.model.event.PostEvent;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PostEventListenerKafka extends AbstractEventHandler<PostEvent> {
    public PostEventListenerKafka(AchievementCache achievementCache,
                                  AchievementService achievementService,
                                  String achievementTitle) {
        super(achievementCache, achievementService, achievementTitle);
    }

    @KafkaListener(topics = "${spring.data.kafka.channel.post_channel}", id = "spring.data.kafka.group")
    public void listen(PostEvent event) {
        super.handle(event);
    }
}
