package faang.school.achievement.listener;

import faang.school.achievement.model.event.PostEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PostEventListenerKafka {

    @KafkaListener(topics = "${spring.data.kafka.channel.post_channel}", id = "spring.data.kafka.group")
    public void listen(PostEvent event) {
        System.out.println(event);
    }
}
