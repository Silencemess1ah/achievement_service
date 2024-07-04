package faang.school.achievement.redis.handler;

import org.springframework.scheduling.annotation.Async;

@Deprecated
public interface DeprecatedEventHandler<E> {
    @Async(value = "taskExecutor")
    void handleEvent(E event);

}
