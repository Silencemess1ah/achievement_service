package faang.school.achievement.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Evgenii Malkov
 */
@Component
public class AchievementCache extends AbstractCacheManager<AchievementDto> {

    private static final String ACHIEVEMENT_CACHE_KEY = "ACHIEVEMENTS";
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;

    public AchievementCache(ObjectMapper mapper, RedisTemplate<String, Object> redisTemplate, AchievementRepository achievementRepository, AchievementMapper achievementMapper) {
        super(mapper, redisTemplate);
        this.achievementRepository = achievementRepository;
        this.achievementMapper = achievementMapper;
    }

    @PostConstruct
    public void fillAchievements() {
        Map<String, AchievementDto> allAchievements = achievementRepository.findAll().stream()
                .collect(Collectors.toMap(Achievement::getTitle, achievementMapper::toDto));
        put(ACHIEVEMENT_CACHE_KEY, allAchievements);
    }

    public AchievementDto getByTitle(String title) {
        Object value = get(ACHIEVEMENT_CACHE_KEY, title);
        if (value == null) {
            return null;
        }
        return mapper.convertValue(value, AchievementDto.class);
    }
}
