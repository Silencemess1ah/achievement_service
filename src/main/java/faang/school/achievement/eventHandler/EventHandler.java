package faang.school.achievement.eventHandler;

import org.springframework.scheduling.annotation.Async;

public interface EventHandler<T> {
    @Async("commentEventTPool")
    public void handle(T event);
}
