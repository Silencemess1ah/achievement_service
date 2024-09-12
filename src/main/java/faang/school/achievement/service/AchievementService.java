package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.SortField;
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
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Evgenii Malkov
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AchievementService {

    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementProgressMapper achievementProgressMapper;
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final List<AchievementFilter> achievementFilters;
    private final UserAchievementRepository userAchievementRepository;
    private final UserAchievementMapper userAchievementMapper;

    public List<AchievementDto> getAchievements(AchievementFilterDto filterDto) {
        Pageable pageable = preparePageRequest(filterDto.getPage(), filterDto.getSize(), filterDto.getSortField(), filterDto.getDirection());

        Stream<Achievement> achievementList = achievementRepository.findAll(pageable).stream();
        return achievementFilters.stream()
                .filter(filter -> filter.isApplicable(filterDto))
                .flatMap(filter -> filter.apply(achievementList, filterDto))
                .map(achievementMapper::toDto)
                .toList();
    }

    public Pageable preparePageRequest(int page, int size, SortField sortField, Sort.Direction direction) {
        Sort sort = Sort.by(SortField.valueOf(sortField.name()).getValue());
        sort = (direction.isAscending()) ? sort.ascending() : sort.descending();
        return PageRequest.of(page, size, sort);
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


    public boolean hasAchievement(long userId, Achievement achievement) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievement.getId());
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    @Transactional(readOnly = true)
    public AchievementProgress getAchievementProgress(long userId, Achievement achievement) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievement.getId()).orElseThrow(() -> {
            String errorMessage = "Couldn't find Achievement Progress entity. User ID = "
                    + userId + " Achievement ID = " + achievement.getId();
            log.error(errorMessage);
            return new EntityNotFoundException(errorMessage);
        });
    }

    @Transactional
    public UserAchievement giveAchievement(Achievement achievement, AchievementProgress achievementProgress) {
        UserAchievement userAchievement = UserAchievement.builder().userId(achievementProgress.getUserId())
                .achievement(achievement)
                .build();
        return userAchievementRepository.save(userAchievement);
    }
}