package faang.school.achievement.service.achievement_progress;

import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.mapper.AchievementProgressMapper;
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
}
