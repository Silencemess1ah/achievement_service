package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.repository.AchievementProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementProgressService {
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementProgressMapper achievementProgressMapper;

    public List<AchievementProgressDto> getAchievementInProgressForUserById(long userId) {
        log.info("The user received all his achievements progress by id: {}", userId);
        return achievementProgressRepository.findByUserId(userId).stream()
                .map(achievementProgressMapper::toDto)
                .toList();
    }
}
