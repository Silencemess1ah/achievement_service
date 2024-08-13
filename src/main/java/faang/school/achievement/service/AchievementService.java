package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.service.filter.AchievementFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final List<AchievementFilter> achievementFilters;

    @Transactional(readOnly = true)
    public List<AchievementDto> getAllAchievement() {
        log.info("Getting all achievements");
        return achievementMapper.toListDto(achievementRepository.findAll());
    }

    @Transactional(readOnly = true)
    public AchievementDto getAchievementById(long achievementId) {
        log.info("Get achievement by id: {}", achievementId);
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(EntityNotFoundException::new);
        return achievementMapper.toDto(achievement);
    }

    public List<AchievementDto> getAchievementByFilter(AchievementFilterDto filters) {
        List<Achievement> allAchievements = achievementRepository.findAll();
        return achievementFilters.stream()
                .filter(filter -> filter.isApplicable(filters))
                .flatMap(filter -> filter.apply(allAchievements, filters))
                .map(achievementMapper::toDto)
                .toList();
    }
}
