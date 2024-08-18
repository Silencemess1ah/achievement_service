package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.exception.ResourceNotFoundException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.filter.AchievementFilter;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final List<AchievementFilter> achievementFilters;
    private final AchievementMapper achievementMapper;
    private final AchievementProgressMapper achievementProgressMapper;
    private final UserAchievementMapper userAchievementMapper;

    @Transactional
    public List<AchievementDto> getAllByFilter(@NotNull AchievementFilterDto filterDto) {
        List<AchievementDto> achievementList = achievementRepository.findAll()
                .stream()
                .map(achievementMapper::toDto)
                .toList();

        return achievementFilters.stream()
                .filter(filter -> filter.isApplicable(filterDto))
                .flatMap(filter -> filter.apply(achievementList.stream(), filterDto))
                .toList();
    }

    @Transactional
    public List<UserAchievementDto> getByUserId(@NotNull Long userId) {
        List<UserAchievement> userAchievements = userAchievementRepository.findByUserId(userId);

        return userAchievements.stream()
                .map(userAchievementMapper::toDto)
                .toList();
    }

    @Transactional
    public AchievementDto getById(@NotNull Long id) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Achievement with %s id doesn't exist", id)));

        return achievementMapper.toDto(achievement);
    }

    @Transactional
    public Achievement getEntityById(@NotNull Long id) {
        return achievementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Achievement with %s id doesn't exist", id)));
    }

    @Transactional
    public List<AchievementProgressDto> getAchievementInProgress(@NotNull Long userId) {
        List<AchievementProgress> byUserId = achievementProgressRepository.findByUserId(userId);

        return byUserId.stream()
                .map(achievementProgressMapper::toDto)
                .toList();
    }
}
