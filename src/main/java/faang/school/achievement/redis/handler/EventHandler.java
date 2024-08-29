package faang.school.achievement.redis.handler;

import org.springframework.scheduling.annotation.Async;

public interface EventHandler<T> {

    @Async("executorService")
    void handleEvent(T event);
}
