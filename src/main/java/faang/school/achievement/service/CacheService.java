package faang.school.achievement.service;

import java.time.Duration;

public interface CacheService<T> {

    void put(String key, T value);

    void put(String key, T value, Duration time);

    T get(String key, Class<T> clazz);

    Boolean existsBy(String key);
}
