package faang.school.achievement.handler;

import org.springframework.scheduling.annotation.Async;
import faang.school.achievement.dto.CommentEvent;

public interface EventHandler<T> {

    @Async("executorService")
    void handleEvent(T event);

    Class<T> getType();

    void checkAndGetAchievement(CommentEvent commentEvent);
}