package faang.school.achievement.handler;

public interface EventHandler<T> {

    void checkAchievement(T event);
}
