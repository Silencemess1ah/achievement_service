package faang.school.achievement.eventHandler;

import org.springframework.scheduling.annotation.Async;

public interface EventHandler<T> {
    @Async("commentEventPool")
    public void handle(T event);
}
