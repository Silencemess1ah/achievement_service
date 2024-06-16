package faang.school.achievement.service.achievement_progress;

import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.exception.NotFoundException;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementProgressServiceImpl implements AchievementProgressService {

    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementProgressMapper achievementProgressMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AchievementProgressDto> getAchievementProgressesByUserId(long userId) {
        return achievementProgressRepository.findByUserId(userId).stream()
                .map(achievementProgressMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    @Override
    @Transactional(readOnly = true)
    public AchievementProgressDto getProgress(long userId, long achievementId) {

        AchievementProgress achievementProgress = achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new NotFoundException("Achievement progress with userId=" + userId +
                        " and achievementId=" + achievementId + " not found"));

        return achievementProgressMapper.toDto(achievementProgress);
    }

    @Override
    @Transactional
    public long incrementAndGetProgress(long userId, long achievementId) {

        AchievementProgress achievementProgress = achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new NotFoundException("Achievement progress with userId=" + userId +
                        " and achievementId=" + achievementId + " not found"));

        achievementProgress.increment();

        //achievementProgressRepository.save(achievementProgress);

        return achievementProgress.getCurrentPoints();
    }
}
