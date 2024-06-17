package faang.school.achievement.service.user_achievement;

import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAchievementServiceImpl implements UserAchievementService {

    private final UserAchievementRepository userAchievementRepository;
    private final UserAchievementMapper userAchievementMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserAchievementDto> getAchievementsByUserId(long userId) {
        return userAchievementRepository.findByUserId(userId).stream()
                .map(userAchievementMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void giveAchievement(long userId, Achievement achievement) {

        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();

        userAchievementRepository.save(userAchievement);

        log.info("Achievement with achievementId={} was given to user with userId={}", achievement.getId(), userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }
}
