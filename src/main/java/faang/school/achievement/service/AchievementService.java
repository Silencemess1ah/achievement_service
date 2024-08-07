package faang.school.achievement.service;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final UserContext userContext;

    public AchievementDto createAchievement(AchievementDto achievementDto) {
        Achievement achievement = achievementMapper.toEntity(achievementDto);
        if (!achievementRepository.existsByTitle(achievement.getTitle())) {
            return achievementMapper.toDto(achievementRepository.save(achievement));
        } else {
            log.error("achievement is not exist for title and userId");
            throw new IllegalArgumentException("achievement is not exist for title and userId");
        }
    }

    public Achievement deleteAchievement(Long achievementId) {
        Optional<Achievement> achievement = achievementRepository.findById(achievementId);
        if (!achievement.isEmpty()) {
            achievementRepository.delete(achievement.get());
            return achievement.get();
        } else {
            log.error("achievement is not exist");
            throw new IllegalArgumentException("achievement is not exist");
        }
    }

    @Transactional
    public AchievementDto createAchievementForUser(Long achievementId) {
        Optional<Achievement> achievement = achievementRepository.findById(achievementId);
        if (achievement.isPresent()) {
            UserAchievement userAchievement = new UserAchievement();
            userAchievement.setUserId(userContext.getUserId());
            userAchievement.setAchievement(achievement.get());
            achievement.get().getUserAchievements().add(userAchievement);
            return achievementMapper.toDto(achievementRepository.save(achievement.get()));
        } else {
            log.error("achievement is not exist for title and userId");
            throw new IllegalArgumentException("achievement is not exist for title and userId");
        }
    }

    @Transactional
    public Achievement deleteAchievementUserFor(Long achievementId) {
        Optional<Achievement> achievement = achievementRepository.findById(achievementId);
        if (!achievement.isEmpty()) {
            Boolean authorIs = achievement.get().getUserAchievements().contains(userContext.getUserId());
            if (authorIs) {
                achievement.get().getUserAchievements().remove(userContext.getUserId());
                achievementRepository.save(achievement.get());
                return achievement.get();
            } else {
                log.error("author achievement in not contain current user");
            }
        } else {
            log.error("achievement is not exist");
            throw new IllegalArgumentException("achievement is not exist");
        }
        return achievement.get();
    }
}