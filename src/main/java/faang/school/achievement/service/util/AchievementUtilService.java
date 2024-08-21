package faang.school.achievement.service.util;

import faang.school.achievement.client.UserServiceClient;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.dto.UserDto;
import faang.school.achievement.exception.NotFoundException;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AchievementUtilService {
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final UserAchievementMapper mapper;
    private final UserServiceClient userServiceClient;

    public AchievementProgress getProgress(Long userId, Long achievementId) {
        UserDto user = getUserDto(userId);
        Achievement achievement = getAchievement(achievementId);
        Optional<AchievementProgress> achievementProgress = achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId);
        if (achievementProgress.isPresent()) {
            log.info("Get progress of achievement {} for user {} ", achievement.getDescription(), user.getUsername());
            return achievementProgress.get();
        } else {
            log.info("user {} does not have progress in achievement {} ", user.getUsername(), achievement.getDescription());
            throw new NotFoundException("user " + user.getUsername() + " does not have progress in achievement " + achievement.getDescription());
        }
    }

    private UserDto getUserDto(long userId) {
        return userServiceClient.getUser(userId);
    }

    private Achievement getAchievement(Long achievementId) {
        return achievementRepository.findById(achievementId).orElseThrow(() -> new NotFoundException("Achievement Does not exist"));
    }

    public void giveAchievement(Long achievementId, Long userId) {
        UserAchievementDto dto = new UserAchievementDto();
        dto.setAchievementId(achievementId);
        dto.setUserId(userId);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        UserAchievement achievement = mapper.toEntity(dto);
        achievement.setAchievement(getAchievement(achievementId));
        userAchievementRepository.save(mapper.toEntity(dto));
    }
}
