package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final AchievementCache achievementCache;
    private final AchievementMapper achievementMapper;

    public List<Achievement> getAchievements() {
        return achievementRepository.findAll();
    }

    @PostConstruct
    public void createAchievementCache() {
        achievementCache.createCache(getAchievements());
    }

    public AchievementDto getAchievementFromCache(String achievementTitle) {
        Achievement achievement = achievementCache.getAchievement(achievementTitle);
        return achievementMapper.mapToDto(achievement);
    }
}
