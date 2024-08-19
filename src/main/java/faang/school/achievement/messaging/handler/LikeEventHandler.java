package faang.school.achievement.messaging.handler;

import faang.school.achievement.event.LikeEvent;
import org.springframework.stereotype.Component;

@Component
public abstract class LikeEventHandler implements EventHandler<LikeEvent> {
    @Override
    public abstract void handle(LikeEvent event);
}
