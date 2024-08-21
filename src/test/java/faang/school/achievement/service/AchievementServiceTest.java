package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.filter.AchievementDescriptionFilter;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.filter.AchievementRarityFilter;
import faang.school.achievement.filter.AchievementTitleFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    private AchievementService achievementService;

    @Spy
    private AchievementMapper achievementMapper;

    @Spy
    private AchievementProgressMapper achievementProgressMapper;

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    private Achievement firstAchievement;
    private AchievementProgressDto fifthAchievementProgressDto;
    private AchievementFilterDto achievementFilterDto;
    private List<Achievement> achievements;
    private List<UserAchievement> userAchievements;
    private List<AchievementProgress> achievementProgresses;
    private long userId;
    private long achievementId;


    @BeforeEach
    public void setUp() {
        userId = 1L;
        achievementId = 2L;
        achievementFilterDto = AchievementFilterDto.builder()
                .titlePattern("title")
                .rarity(Rarity.COMMON)
                .descriptionPattern("description")
                .build();
        List<AchievementFilter> achievementFiltersImpl = List.of(
                new AchievementDescriptionFilter(),
                new AchievementRarityFilter(),
                new AchievementTitleFilter()
        );
        achievementService = AchievementService.builder()
                .achievementMapper(achievementMapper)
                .achievementProgressMapper(achievementProgressMapper)
                .achievementRepository(achievementRepository)
                .userAchievementRepository(userAchievementRepository)
                .achievementProgressRepository(achievementProgressRepository)
                .achievementFilters(achievementFiltersImpl)
                .build();

        firstAchievement = Achievement.builder()
                .title("Title")
                .rarity(Rarity.COMMON)
                .description("Description")
                .build();
        Achievement secondAchievement = Achievement.builder()
                .title("not")
                .rarity(Rarity.COMMON)
                .description("Description")
                .build();
        Achievement thirdAchievement = Achievement.builder()
                .title("Title")
                .rarity(Rarity.RARE)
                .description("Description")
                .build();
        Achievement fourthAchievement = Achievement.builder()
                .title("Title")
                .rarity(Rarity.COMMON)
                .description("not")
                .build();
        Achievement fifthAchievement = Achievement.builder()
                .id(5L)
                .build();

        achievements = List.of(
                firstAchievement,
                secondAchievement,
                thirdAchievement,
                fourthAchievement);

        userAchievements = List.of(
                UserAchievement.builder()
                        .userId(userId)
                        .achievement(firstAchievement)
                        .build(),
                UserAchievement.builder()
                        .userId(userId)
                        .achievement(secondAchievement)
                        .build(),
                UserAchievement.builder()
                        .userId(userId)
                        .achievement(thirdAchievement)
                        .build(),
                UserAchievement.builder()
                        .userId(userId)
                        .achievement(fourthAchievement)
                        .build()
        );

        achievementProgresses = List.of(
                AchievementProgress.builder()
                        .achievement(fifthAchievement)
                        .build(),
                AchievementProgress.builder()
                        .achievement(firstAchievement)
                        .build(),
                AchievementProgress.builder()
                        .achievement(secondAchievement)
                        .build()
        );

        fifthAchievementProgressDto = AchievementProgressDto.builder()
                .id(fifthAchievement.getId())
                .build();
    }

    @Test
    @DisplayName("testing getAllAchievements method")
    void testGetAllAchievements() {
        when(achievementRepository.findAll()).thenReturn(achievements);
        List<AchievementDto> resultAchievements = achievementService.getAllAchievements(achievementFilterDto);
        verify(achievementRepository, times(1)).findAll();

        assertEquals(1, resultAchievements.size());
        assertEquals(achievementMapper.toDto(firstAchievement), resultAchievements.get(0));
    }

    @Test
    @DisplayName("testing getAchievementsByUserId method")
    void testGetAchievementsBuUserId() {
        when(userAchievementRepository.findByUserId(userId)).thenReturn(userAchievements);
        List<AchievementDto> resultAchievements = achievementService.getAchievementsByUserId(userId);
        verify(userAchievementRepository, times(1)).findByUserId(userId);

        assertEquals(userAchievements.size(), resultAchievements.size());
        assertIterableEquals(achievements.stream().map(achievementMapper::toDto).toList(), resultAchievements);
    }

    @Nested
    @DisplayName("Method: getAchievementById")
    class getAchievementById {

        @Test
        @DisplayName("testing getAchievementById method with achievement presence")
        void testGetAchievementByIdWithAchievementAbsence() {
            when(achievementRepository.findById(achievementId)).thenReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class, () -> achievementService.getAchievementById(achievementId));
            verify(achievementRepository, times(1)).findById(achievementId);
        }

        @Test
        @DisplayName("testing getAchievementById method with achievement presence")
        void testGetAchievementByIdWithAchievementPresence() {
            when(achievementRepository.findById(achievementId)).thenReturn(Optional.ofNullable(firstAchievement));
            AchievementDto achievementDto = achievementService.getAchievementById(achievementId);
            assertEquals(achievementMapper.toDto(firstAchievement), achievementDto);
            verify(achievementRepository, times(1)).findById(achievementId);
        }
    }

    @Test
    @DisplayName("testing getUserNotAttainedAchievements")
    void testGetUserNotAttainedAchievements() {
        when(userAchievementRepository.findByUserId(userId)).thenReturn(userAchievements);
        when(achievementProgressRepository.findByUserId(userId)).thenReturn(achievementProgresses);
        when(achievementProgressMapper.toDto(achievementProgresses.get(0))).thenReturn(fifthAchievementProgressDto);
        List<AchievementProgressDto> userNotAttainedAchievements =
                achievementService.getUserNotAttainedAchievements(userId);
        System.out.println(userNotAttainedAchievements);
        assertEquals(1, userNotAttainedAchievements.size());
        assertIterableEquals(List.of(fifthAchievementProgressDto), userNotAttainedAchievements);
    }
}