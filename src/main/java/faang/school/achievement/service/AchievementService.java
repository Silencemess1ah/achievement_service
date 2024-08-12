package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final AchievementCache achievementCache;

    @Transactional(readOnly = true)
    public AchievementDto getAchievementByTitle(String title) {
        Optional<Achievement> cachedAchievement = achievementCache.getAchievementByTitle(title);
        Achievement achievement = cachedAchievement.orElseGet(() -> achievementRepository.findByTitle(title)
            .orElseThrow(() -> new EntityNotFoundException("Achievement with title: %s not found."
                .formatted(title))));

        return achievementMapper.toDto(achievement);
    }
}
