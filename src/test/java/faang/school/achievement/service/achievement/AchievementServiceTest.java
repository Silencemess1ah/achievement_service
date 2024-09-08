package faang.school.achievement.service.achievement;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.mapper.AchievementMapperImpl;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.achievement.filter.AchievementFilter;
import faang.school.achievement.service.achievement.filter.DescriptionFilter;
import faang.school.achievement.service.achievement.filter.NameFilter;
import faang.school.achievement.service.achievement.filter.RarityFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {
    @Mock
    private UserContext userContext;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Spy
    private AchievementMapperImpl achievementMapper;
    @Mock
    private List<AchievementFilter> achievementFilters;
    @InjectMocks
    private AchievementService achievementService;
    private final Achievement achievement1 = Achievement.builder()
            .title("AchievementTitle1")
            .description("AchievementDescription1")
            .rarity(Rarity.COMMON)
            .build();
    private final Achievement achievement2 = Achievement.builder()
            .title("AchievementTitle2")
            .description("AchievementDescription2")
            .rarity(Rarity.EPIC)
            .build();

    private long userId = 1L;
    private long achievementId = 1L;
    private AchievementProgress progress = new AchievementProgress();


    @Test
    public void testGetAchievementByFilter() {
        Iterable<Achievement> iterable = () -> List.of(achievement1, achievement2).iterator();
        List<AchievementFilter> filters = List.of(new RarityFilter(), new NameFilter(), new DescriptionFilter());
        AchievementFilterDto filterDto = new AchievementFilterDto("Achievement", "Achievement", Rarity.COMMON);
        List<AchievementDto> expected = List.of(achievementMapper.toDto(achievement1));

        when(achievementRepository.findAll()).thenReturn(iterable);
        when(achievementFilters.stream()).thenReturn(filters.stream());

        List<AchievementDto> result = achievementService.getAchievementsByFilter(filterDto);
        assertEquals(expected, result);
    }

    @Test
    public void testGetUserAchievementsByUserId() {
        UserAchievement userAchievement1 = UserAchievement.builder().id(1L).build();
        UserAchievement userAchievement2 = UserAchievement.builder().id(2L).build();
        List<UserAchievement> userAchievementList = List.of(userAchievement1, userAchievement2);
        List<UserAchievementDto> expected = userAchievementList.stream().map(achievementMapper::toDto).toList();

        when(userContext.getUserId()).thenReturn(1L);
        when(userAchievementRepository.findByUserId(1L)).thenReturn(userAchievementList);

        List<UserAchievementDto> result = achievementService.getUserAchievementsByUserId();
        assertEquals(expected, result);
    }

    @Test
    public void testGetAchievementById() {
        AchievementDto expected = achievementMapper.toDto(achievement1);

        when(achievementRepository.findById(1L)).thenReturn(Optional.of(achievement1));

        AchievementDto result = achievementService.getAchievementById(1L);
        assertEquals(expected, result);
    }

    @Test
    public void testGetAchievementByIdNotExist() {
        when(achievementRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> achievementService.getAchievementById(1L));
    }

    @Test
    public void testGetInProgressUserAchievementsByUserId() {
        AchievementProgress achievementProgress1 = AchievementProgress.builder().id(1L).build();
        AchievementProgress achievementProgress2 = AchievementProgress.builder().id(2L).build();
        List<AchievementProgress> achievementProgressList = List.of(achievementProgress1, achievementProgress2);
        List<AchievementProgressDto> expected = achievementProgressList.stream().map(achievementMapper::toDto).toList();

        when(userContext.getUserId()).thenReturn(1L);
        when(achievementProgressRepository.findByUserId(1L)).thenReturn(achievementProgressList);

        List<AchievementProgressDto> result = achievementService.getInProgressUserAchievementsByUserId();
        assertEquals(expected, result);
    }

    @Test
    public void testHasAchievement() {
        achievementService.hasAchievement(userId, achievementId);

        verify(userAchievementRepository).existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    public void testCreateProgressIfNecessary() {
        achievementService.createProgressIfNecessary(userId, achievementId);

        verify(achievementProgressRepository).createProgressIfNecessary(userId, achievementId);
    }

    @Test
    public void testGetProgressWithWrongId() {
        String message = "User with id " + userId + "hasn't achievement progress by achievement id: " + achievementId;

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                ()-> achievementService.getProgress(userId, achievementId));

        assertEquals(message, e.getMessage());
    }

    @Test
    public void testGetProgress() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.of(progress));

        AchievementProgress result = achievementService.getProgress(userId, achievementId);

        assertEquals(progress, result);
    }

    @Test
    public void testGiveAchievement() {
        Achievement achievement = Achievement.builder()
                .id(achievementId)
                .build();
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();

        achievementService.giveAchievement(userId, achievement);
        verify(userAchievementRepository).save(userAchievement);
    }

    @Test
    public void testUpdateProgress() {
        achievementService.updateProgress(progress);

        verify(achievementProgressRepository).save(progress);
    }
}
