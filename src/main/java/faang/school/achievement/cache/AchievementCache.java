package faang.school.achievement.cache;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.validator.AchievementValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AchievementCache {
    private final RedisTemplate<String, Object> redisTemplate;
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final AchievementValidator achievementValidator;
    @Value("${spring.data.redis.achievement-cache-key}")
    private String achievementHashKey;

    @PostConstruct
    public void init() {
        List<AchievementDto> allAchievements = achievementMapper.toListDto(achievementRepository.findAll());
        Map<String, AchievementDto> achievementsMap = allAchievements.stream()
                .collect(Collectors.toMap(AchievementDto::getTitle, achievementDto -> achievementDto));
        HashOperations<String, String, AchievementDto> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(achievementHashKey, achievementsMap);
    }

    public AchievementDto get(String achievementTitle) {
        achievementValidator.checkTitle(achievementTitle);
        HashOperations<String, String, AchievementDto> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(achievementHashKey, achievementTitle);
    }
}