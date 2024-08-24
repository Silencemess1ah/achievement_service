package faang.school.achievement.handler;

public interface EventHandler<U> {
    void reaction(U event);
}