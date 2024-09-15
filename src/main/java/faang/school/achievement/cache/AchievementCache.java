package faang.school.achievement.cache;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.validator.AchievementValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AchievementCache {
    private final RedisTemplate<String, Object> redisTemplate;
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final AchievementValidator achievementValidator;
    private final ChannelTopic redisChannelTopic;
    @Value("${spring.data.redis.achievement-cache-key}")
    private String achievementCacheKey;
    @Value("${spring.data.redis.cache-ttl-hours}")
    private int cacheTTL;

    @PostConstruct
    public void init() {
        redisTemplate.convertAndSend(redisChannelTopic.getTopic(), "init");
    }

    public AchievementDto getAchievementFromCache(String achievementTitle) {
        achievementValidator.checkTitle(achievementTitle);
        return getAchievementFromRedis(achievementTitle);
    }

    @Transactional(readOnly = true)
    public AchievementDto updateAchievementInCache(String achievementTitle) {
        Achievement achievement = achievementRepository.findByTitle(achievementTitle).orElseThrow(
                () -> new EntityNotFoundException("Achievement not found by title: " + achievementTitle));
        return achievementMapper.toDto(achievement);
    }

    private AchievementDto getAchievementFromRedis(String achievementTitle) {
        String buildCacheKey = achievementCacheKey + ":" + achievementTitle;
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        AchievementDto cachedAchievement = (AchievementDto) valueOps.get(buildCacheKey);
        if (cachedAchievement == null) {
            cachedAchievement = updateAchievementInCache(achievementTitle);
            valueOps.set(buildCacheKey, cachedAchievement, cacheTTL, TimeUnit.HOURS);
        }
        return cachedAchievement;
    }
}