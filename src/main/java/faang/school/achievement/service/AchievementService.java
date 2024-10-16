package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementCache achievementCache;

    @PostConstruct
    public void initCache() {
        achievementRepository.findAll().forEach(achievement ->
                achievementCache.getCache().put(achievement.getTitle(), achievement));
    }
}
