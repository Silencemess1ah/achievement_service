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
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {


            @Mock
            AchievementRepository achievementRepository;

    @Mock
    UserAchievementRepository userAchievementRepository;

    @Mock
    AchievementProgressRepository achievementProgressRepository;

    @Mock
    AchievementMapper achievementMapper;

    @Mock
    UserAchievementMapper userAchievementMapper;

    @Mock
    AchievementProgressMapper achievementProgressMapper;

    @Mock
    AchievementFilter achievementFilter;

    @Mock
    UserContext userContext;

    @Mock
    AchievementMapper achievementMapper;

    @Mock
    AchievementCache achievementCache;

    @InjectMocks
    AchievementService achievementService;

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
    long userId;
    long achievementId;
    Achievement achievement;
    AchievementDto achievementDto;
    AchievementFilterDto achievementFilterDto;
    UserAchievement userAchievement;
    UserAchievementDto userAchievementDto;
    AchievementProgress achievementProgress;
    AchievementProgressDto achievementProgressDto;
    List<Achievement> achievements;
    List<AchievementDto> achievementsDto;
    List<UserAchievement> userAchievements;
    List<UserAchievementDto> userAchievementsDto;
    List<AchievementProgress> achievementProgresses;
    List<AchievementProgressDto> achievementProgressesDto;
    String achievementTitle;
    Achievement achievement;
    AchievementDto achievementDto;


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

        achievementService = new AchievementService(
                achievementRepository,
                userAchievementRepository,
                achievementProgressRepository,
                achievementMapper,
                userAchievementMapper,
                achievementProgressMapper,
                List.of(achievementFilter),
                userContext
        );

        userId = 1;
        achievementId = 1;
        achievement = new Achievement();
        achievementDto = AchievementDto.builder().build();
        achievementFilterDto = AchievementFilterDto.builder().build();
        userAchievement = new UserAchievement();
        userAchievementDto = UserAchievementDto.builder().build();
        achievementProgress = new AchievementProgress();
        achievementProgressDto = AchievementProgressDto.builder().build();
        achievements = List.of(achievement);
        achievementsDto = List.of(achievementDto);
        userAchievements = List.of(userAchievement);
        userAchievementsDto = List.of(userAchievementDto);
        achievementProgresses = List.of(achievementProgress);
        achievementProgressesDto = List.of(achievementProgressDto);
        achievementTitle = "title";
        achievement = new Achievement();
        achievementDto = AchievementDto.builder().build();
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


    @Test
    @DisplayName("Should return list of AchievementDto when filtering achievements by filter")
    void getAchievementsByFilter() {
        when(achievementRepository.findAll()).thenReturn(achievements);
        when(achievementFilter.isApplicable(achievementFilterDto)).thenReturn(true);
        when(achievementFilter.apply(any(), any())).thenReturn(achievements.stream());
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        List<AchievementDto> result = achievementService.getAchievementsByFilter(achievementFilterDto);

        verify(achievementRepository).findAll();
        verify(achievementFilter).isApplicable(achievementFilterDto);
        verify(achievementFilter).apply(any(), any());
        verify(achievementMapper).toDto(achievement);
        assertNotNull(result);
        assertEquals(achievementsDto, result);
    }

    @Test
    @DisplayName("Should return list of UserAchievementDto when retrieving achievements by user ID")
    void getAchievementsByUserId() {
        when(userContext.getUserId()).thenReturn(userId);
        when(userAchievementRepository.findByUserId(userId)).thenReturn(userAchievements);
        when(userAchievementMapper.toDto(userAchievement)).thenReturn(userAchievementDto);

        List<UserAchievementDto> result = achievementService.getAchievementsByUserId();

        verify(userAchievementRepository).findByUserId(userId);
        verify(userAchievementMapper).toDto(userAchievement);
        assertNotNull(result);
        assertEquals(userAchievementsDto, result);
    }

    @Test
    @DisplayName("Should return AchievementDto when retrieving achievement by ID")
    void getAchievementById() {
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        AchievementDto result = achievementService.getAchievementById(achievementId);

        verify(achievementRepository).findById(achievementId);
        verify(achievementMapper).toDto(achievement);
        assertNotNull(result);
        assertEquals(achievementDto, result);
    }
    
    @Test  
    @DisplayName("Should return list of AchievementProgressDto when retrieving achievement progress by user ID")
    void getAchievementProgressByUserId() {
        when(userContext.getUserId()).thenReturn(userId);
        when(achievementProgressRepository.findByUserId(userId)).thenReturn(achievementProgresses);
        when(achievementProgressMapper.toDto(achievementProgress)).thenReturn(achievementProgressDto);

        List<AchievementProgressDto> result = achievementService.getAchievementProgressByUserId();

        verify(achievementProgressRepository).findByUserId(userId);
        verify(achievementProgressMapper).toDto(achievementProgress);
        assertNotNull(result);
        assertEquals(achievementProgressesDto, result);
    }

    @Test
    @DisplayName("Should return AchievementDto when achievement is found in cache")
    void getAchievementByTitle_FoundInCache() {
        when(achievementCache.getAchievementByTitle(achievementTitle)).thenReturn(Optional.of(achievement));
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        AchievementDto result = achievementService.getAchievementByTitle(achievementTitle);

        verify(achievementCache).getAchievementByTitle(achievementTitle);
        verify(achievementMapper).toDto(achievement);
        assertNotNull(result);
        assertEquals(achievementDto, result);
    }

    @Test
    @DisplayName("Should return AchievementDto when achievement is found in repository after cache miss")
    void getAchievementByTitle_NotFoundInCache() {
        when(achievementCache.getAchievementByTitle(achievementTitle)).thenReturn(Optional.empty());
        when(achievementRepository.findByTitle(achievementTitle)).thenReturn(Optional.of(achievement));
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        AchievementDto result = achievementService.getAchievementByTitle(achievementTitle);

        verify(achievementCache).getAchievementByTitle(achievementTitle);
        verify(achievementRepository).findByTitle(achievementTitle);
    }
    
      @Test
      @DisplayName("Should throw EntityNotFoundException when achievement is not found in cache or repository")
    void getAchievementByTitle_NotFound() {
        when(achievementCache.getAchievementByTitle(achievementTitle)).thenReturn(Optional.empty());
        when(achievementRepository.findByTitle(achievementTitle)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> achievementService.getAchievementByTitle(achievementTitle));
    }
}