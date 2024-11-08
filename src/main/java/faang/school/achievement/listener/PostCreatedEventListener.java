package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.PostCreatedEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostCreatedEventListener extends AbstractEventListener<PostCreatedEvent> {
    private final Topic topic;

    public PostCreatedEventListener(ObjectMapper objectMapper, List<EventHandler<PostCreatedEvent>> eventHandlers,
                                    @Value("${spring.data.redis.topic.post.post-created}") String postCreatedTopic) {
        super(objectMapper, PostCreatedEvent.class, eventHandlers);
        topic = new ChannelTopic(postCreatedTopic);
    }

    @Override
    public Topic getTopic() {
        return topic;
    }
}
