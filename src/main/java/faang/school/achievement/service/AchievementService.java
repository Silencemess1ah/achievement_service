package faang.school.achievement.service;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementMapper achievementMapper;
    private final UserContext userContext;
    private final List<AchievementFilter> achievementsFilter;

    public AchievementDto createAchievement(AchievementDto achievementDto) {
        Achievement achievement = achievementMapper.toEntity(achievementDto);
        if (!achievementRepository.existsByTitle(achievement.getTitle())) {
            return achievementMapper.toDto(achievementRepository.save(achievement));
        } else {
            log.error("achievement is not exist for title and userId");
            throw new IllegalArgumentException("achievement is not exist for title and userId");
        }
    }

    public Achievement deleteAchievement(Long achievementId) {
        Optional<Achievement> achievement = achievementRepository.findById(achievementId);
        if (!achievement.isEmpty()) {
            achievementRepository.delete(achievement.get());
            return achievement.get();
        } else {
            log.error("achievement is not exist");
            throw new IllegalArgumentException("achievement is not exist");
        }
    }

    @Transactional
    public UserAchievement createAchievementForUser(Long achievementId) {
        Optional<Achievement> achievement = achievementRepository.findById(achievementId);
        UserAchievement userAchievement = new UserAchievement();
        if (achievement.isPresent()) {
            userAchievement.setAchievement(achievement.get());
            userAchievement.setUserId(userContext.getUserId());
            userAchievementRepository.save(userAchievement);
            return userAchievement;
        } else {
            log.error("achievement is not exist for title and userId");
            throw new IllegalArgumentException("achievement is not exist for title and userId");
        }
    }

    @Transactional
    public Achievement deleteAchievementForUser(Long achievementId) {
        Optional<Achievement> achievement = achievementRepository.findById(achievementId);
        Optional<UserAchievement> userAchievement = userAchievementRepository.findByAchievement(achievementId);
        if (!achievement.isEmpty() && !userAchievement.isEmpty()) {
            userAchievementRepository.delete(userAchievement.get());
        } else {
            log.error("achievement or user achievement is not exist");
            throw new IllegalArgumentException("achievement or user achievement is not exist");
        }
        return achievement.get();
    }

    @Transactional
    public List<AchievementDto> getAllAchievement(AchievementFilterDto achievementFilterDto) {
        if (achievementFilterDto == null) {
            log.error("achievement filter is null");
            throw new IllegalArgumentException("achievement filter is null");
        }

        Iterable<Achievement> achievements = achievementRepository.findAll();
        Stream<Achievement> achievementsStream = StreamSupport.stream(achievements.spliterator(), false);

        return achievementsFilter.stream()
                .filter(achievementFilter -> achievementFilter.isApplicable(achievementFilterDto))
                .reduce(achievementsStream, (cumulativeStream, achievementsFilter) ->
                        achievementsFilter.apply(cumulativeStream, achievementFilterDto), Stream::concat)
                .map(achievementMapper::toDto)
                .toList();
    }

    @Transactional
    public Optional<Achievement> getAllAchievementForUser(Long userId) {
        return achievementRepository.findAllAchievementForUser(userId);
    }

    @Transactional
    public Optional<Achievement> getAchievement(Long achievementId) {
        return achievementRepository.findById(achievementId);
    }

    @Transactional
    public List<Optional<Achievement>> getNoAchievement(Long userId) {
        List<AchievementProgress> achievementProgresses = achievementProgressRepository.findByUserId(userId);
        List<Optional<Achievement>> achievements = new ArrayList<>();
        achievementProgresses.stream()
                .map(achievementProgresse ->
                        achievements.add(achievementRepository.findById(achievementProgresse.getAchievement().getId())))
                .collect(Collectors.toList());
        return achievements;
    }
}