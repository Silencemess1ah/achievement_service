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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<AchievementDto> getPageableAchievements(Pageable pageable) {
        log.info("Getting all achievements with pagination");
        return achievementRepository.findAll(pageable)
                .map(achievementMapper::toDto);
    }

    @Transactional(readOnly = true)
    public AchievementDto getAchievementById(long achievementId) {
        log.info("Get achievement by id: {}", achievementId);
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(EntityNotFoundException::new);
        return achievementMapper.toDto(achievement);
    }

    @Transactional(readOnly = true)
    public Page<AchievementDto> getAchievementByFilter(AchievementFilterDto filters, Pageable pageable) {
        Page<Achievement> pagedAchievements = achievementRepository.findAll(pageable);
        List<Achievement> filteredAchievements = achievementFilters.stream()
                .filter(filter -> filter.isApplicable(filters))
                .flatMap(filter -> filter.apply(pagedAchievements.getContent(), filters))
                .toList();
        return new PageImpl<>(
                filteredAchievements.stream()
                        .map(achievementMapper::toDto)
                        .toList(),
                pageable,
                pagedAchievements.getTotalElements()
        );
    }
}
