package faang.school.achievement.service;

public interface CacheService<T> {

    void put(String key, T value);

    T get(String key, Class<T> clazz);

    Boolean existsBy(String key);
}
