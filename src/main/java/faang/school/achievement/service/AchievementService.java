package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.excepion.DataValidationException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static faang.school.achievement.excepion.message.ExceptionMessage.NO_ACHIEVEMENT_IN_DB;
import static faang.school.achievement.excepion.message.ExceptionMessage.NO_ACHIEVEMENT_PROGRESS;

@Service
public class AchievementService {

    private AchievementProgressRepository achievementProgressRepository;

    private UserAchievementRepository userAchievementRepository;

    private AchievementRepository achievementRepository;

    private AchievementMapper achievementMapper;

    private HashMap<String, Achievement> cash;

    private Map<String, Achievement> getCash() {
        return StreamSupport.stream(achievementRepository.findAll().spliterator(), false)
                .collect(Collectors.toMap(Achievement::getTitle,
                        achievement -> achievement));
    }

    @Transactional(readOnly = true)
    public AchievementDto getAchievementByTitle(String title) {

        Achievement achievement = getCash().get(title)
                .orElseThrow(() -> new DataValidationException(NO_ACHIEVEMENT_IN_DB.getMessage()));

        return achievementMapper.toDto(achievement);
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    @Transactional(readOnly = true)
    public AchievementProgress getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new DataValidationException(NO_ACHIEVEMENT_PROGRESS.getMessage()));
    }

    @Transactional
    public void giveAchievement(long userId, Achievement achievement) {
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();

        userAchievementRepository.save(userAchievement);
    }

    @Transactional(readOnly = true)
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }
}
