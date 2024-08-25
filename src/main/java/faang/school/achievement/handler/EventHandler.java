package faang.school.achievement.handler;

public interface EventHandler<U> {
    void process(U event);
}