package faang.school.achievement.service.achievement;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.exception.NotFoundException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.service.achievement.filter.AchievementFilterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementFilterService achievementFilterService;
    private final AchievementMapper achievementMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AchievementDto> getAchievements(AchievementFilterDto filters) {

        Stream<Achievement> achievements = achievementRepository.findAll().stream();

        return achievementFilterService.applyFilters(achievements, filters)
                .map(achievementMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AchievementDto getAchievementByAchievementId(long achievementId) {

        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new NotFoundException("Achievement with achievementId " + achievementId + " not found"));

        return achievementMapper.toDto(achievement);
    }

    @Override
    @Transactional(readOnly = true)
    public AchievementDto getAchievementByTitle(String title) {

        Achievement achievement = achievementRepository.findByTitle(title)
                .orElseThrow(() -> new NotFoundException("Achievement with title=" + title + " not found"));

        return achievementMapper.toDto(achievement);
    }
}
