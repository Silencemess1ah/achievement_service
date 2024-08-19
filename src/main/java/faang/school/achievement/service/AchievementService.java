package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final List<AchievementFilter> achievementFilters;
    private final AchievementMapper achievementMapper;
    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressMapper achievementProgressMapper;

    public List<AchievementDto> getAchievementsWithFilter(AchievementFilterDto achievementFilterDto) {
        return achievementMapper.toDtos(achievementFilters.stream()
                .filter(achievementFilter -> achievementFilter.isApplicable(achievementFilterDto))
                .reduce(achievementRepository.findAll(), (filtered, filter) ->
                        filter.apply(filtered.stream(), achievementFilterDto).toList(), (a, b) -> b));
    }

    public List<AchievementDto> getAllAchievement(int page, int size) {
        return achievementMapper.toDtos(achievementRepository.findAll(
                PageRequest.of(page, size)).getContent());
    }

    public List<AchievementDto> getUserAchievements(Long userId) {
        return achievementMapper.toDtos(getReceivedUserAchievements(userId).stream()
                .map(UserAchievement::getAchievement)
                .toList());
    }

    public AchievementDto getAchievementById(Long achievementId) {
        return achievementMapper.toDto(findAchievementById(achievementId));
    }

    public List<AchievementProgressDto> getAchievementProgressByUserId(Long userId) {
        return achievementProgressMapper.toDtos(achievementProgressRepository.findByUserId(userId));
    }

    private List<UserAchievement> getReceivedUserAchievements(Long userId) {
        return userAchievementRepository.findByUserId(userId);
    }

    private Achievement findAchievementById(Long achievementId) {
        return achievementRepository.findById(achievementId)
                .orElseThrow(() -> {
                    log.error(String.format("Achievement with id %d not found", achievementId));
                    return new EntityNotFoundException(
                            String.format("Achievement with id %d not found", achievementId));
                });
    }
}