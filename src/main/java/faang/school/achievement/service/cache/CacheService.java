package faang.school.achievement.service.cache;

public interface CacheService<T> {

    void setCacheValue(String key, T value);

    T getCacheValue(String key, Class<T> clazz);
}
