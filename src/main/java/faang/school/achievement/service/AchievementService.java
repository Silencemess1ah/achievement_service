package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.exception.BadRequestException;
import faang.school.achievement.filter.achievement.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Evgenii Malkov
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AchievementService {

    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementProgressMapper achievementProgressMapper;
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final List<AchievementFilter> achievementFilters;
    private final UserAchievementRepository userAchievementRepository;
    private final UserAchievementMapper userAchievementMapper;

    public List<AchievementDto> getAchievements(AchievementFilterDto filterDto) {
        Stream<Achievement> achievements = achievementRepository.findAll().stream();
        Predicate<Achievement> combinedPredicate = achievementFilters.stream()
                .filter(filter -> filter.isApplicable(filterDto))
                .map(filter -> (Predicate<Achievement>) achievement ->
                        filter.apply(Stream.of(achievement), filterDto).findFirst().isPresent()
                )
                .reduce(Predicate::and)
                .orElse(achievement -> true);

        return achievements
                .filter(combinedPredicate)
                .map(achievementMapper::toDto)
                .toList();
    }

    public AchievementDto getAchievementById(long id) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("Achievement with id: %s not found", id)));
        return achievementMapper.toDto(achievement);
    }

    public List<UserAchievementDto> getUserAchievements(long userId) {
        List<UserAchievement> achievements = userAchievementRepository.findByUserId(userId);
        return userAchievementMapper.toListDto(achievements);
    }

    public List<AchievementProgressDto> getUserAchievementsInProgress(long userId) {
        List<AchievementProgress> achievements = achievementProgressRepository.findByUserId(userId);
        return achievementProgressMapper.toListDto(achievements);
    }
}
