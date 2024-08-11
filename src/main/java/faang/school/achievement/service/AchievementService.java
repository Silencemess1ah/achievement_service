package faang.school.achievement.service;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
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
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementMapper achievementMapper;
    private final UserAchievementMapper userAchievementMapper;
    private final AchievementProgressMapper achievementProgressMapper;
    private final List<AchievementFilter> achievementFilters;
    private final UserContext userContext;

    @Transactional(readOnly = true)
    public List<AchievementDto> getAchievementsByFilter(AchievementFilterDto achievementFilterDto) {
        List<Achievement> achievements = achievementRepository.findAll();
        return achievementFilters.stream()
            .filter(achievementFilter -> achievementFilter.isApplicable(achievementFilterDto))
            .reduce(achievements.stream(), (stream, filter) -> filter.apply(stream, achievementFilterDto), Stream::concat)
            .map(achievementMapper::toDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<UserAchievementDto> getAchievementsByUserId() {
        long userId = userContext.getUserId();
        List<UserAchievement> userAchievements = userAchievementRepository.findByUserId(userId);
        return userAchievements.stream()
            .map(userAchievementMapper::toDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public AchievementDto getAchievementById(long id) {
        Achievement achievement = achievementRepository.getById(id);
        return achievementMapper.toDto(achievement);
    }

    @Transactional(readOnly = true)
    public List<AchievementProgressDto> getAchievementProgressByUserId() {
        long userId = userContext.getUserId();
        List<AchievementProgress> achievementProgresses = achievementProgressRepository.findByUserId(userId);
        return achievementProgresses.stream()
            .map(achievementProgressMapper::toDto)
            .toList();
    }
}