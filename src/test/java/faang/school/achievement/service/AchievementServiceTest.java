package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.event.AchievementEvent;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.filter.achievement.AchievementDescriptionFilter;
import faang.school.achievement.filter.achievement.AchievementFilter;
import faang.school.achievement.filter.achievement.AchievementRarityFilter;
import faang.school.achievement.filter.achievement.AchievementTitleFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.AchievementEventPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class AchievementServiceTest {

    @InjectMocks
    private AchievementService achievementService;

    @Mock
    private AchievementMapper achievementMapper;
    @Mock
    private UserAchievementMapper userAchievementMapper;
    @Mock
    private AchievementProgressMapper achievementProgressMapper;
    @Mock
    private UserContext userContext;
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private AchievementEventPublisher achievementEventPublisher;


    private AchievementFilterDto achievementFilterDto;
    private long userId;
    private long achievementId;
    private Achievement achievement;
    private AchievementDto achievementDto;
    private AchievementProgress achievementProgress;
    private AchievementProgressDto achievementProgressDto;
    private UserAchievement userAchievement;
    private UserAchievementDto userAchievementDto;
    private String achievementTitle;
    private String sortField;

    @Captor
    private ArgumentCaptor<UserAchievement> userAchievementArgumentCaptor;

    @BeforeEach
    public void setUp() {
        userId = 1L;
        achievementId = 2L;
        achievementTitle = "achievementTitle";
        sortField = "title";
        String achievementDescription = "achievementDescription";
        Rarity achievementRarity = Rarity.COMMON;

        achievement = Achievement.builder()
                .title(achievementTitle)
                .rarity(achievementRarity)
                .description(achievementDescription)
                .build();
        achievementDto = AchievementDto.builder()
                .title(achievementTitle)
                .rarity(achievementRarity)
                .description(achievementDescription)
                .build();
        achievementFilterDto = AchievementFilterDto.builder()
                .titlePattern(achievementTitle)
                .rarity(achievementRarity)
                .descriptionPattern(achievementDescription)
                .build();

        achievementProgress = AchievementProgress.builder().build();
        achievementProgressDto = AchievementProgressDto.builder().build();
        userAchievement = UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .build();
        userAchievementDto = UserAchievementDto.builder().build();

        List<AchievementFilter> achievementFiltersImpl = List.of(
                new AchievementDescriptionFilter(),
                new AchievementRarityFilter(),
                new AchievementTitleFilter()
        );


        achievementService = AchievementService.builder()
                .userContext(userContext)
                .achievementMapper(achievementMapper)
                .userAchievementMapper(userAchievementMapper)
                .achievementProgressMapper(achievementProgressMapper)
                .achievementRepository(achievementRepository)
                .userAchievementRepository(userAchievementRepository)
                .achievementProgressRepository(achievementProgressRepository)
                .achievementFilters(achievementFiltersImpl)
                .achievementCache(achievementCache)
                .achievementEventPublisher(achievementEventPublisher)
                .build();
    }


    @Test
    @DisplayName("Grant an achievement and verify repository interactions and event publishing")
    void grantAchievement() {
        when(userContext.getUserId()).thenReturn(userId);
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));

        achievementService.grantAchievement(achievementId);

        verify(achievementRepository).findById(achievementId);
        verify(achievementEventPublisher).publish(any(AchievementEvent.class));
        verify(userAchievementRepository).save(any(UserAchievement.class));
    }

    @Test
    @DisplayName("Should return list of AchievementDto when filtering achievements by filter")
    void getAchievementsByFilter() {
        int offset = 1;
        int limit = 10;
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(sortField));
        PageImpl<Achievement> achievementPage = new PageImpl<>(List.of(achievement));
        when(achievementRepository.findAll(pageRequest))
                .thenReturn(achievementPage);
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        List<AchievementDto> resultAchievements = achievementService
                .getAchievementsByFilter(achievementFilterDto, offset, limit, sortField);

        verify(achievementRepository, times(1)).findAll(pageRequest);
        assertEquals(1, resultAchievements.size());
        assertIterableEquals(List.of(achievementDto), resultAchievements);
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
            when(achievementRepository.findById(achievementId)).thenReturn(Optional.ofNullable(achievement));
            when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

            AchievementDto achievementDtoResult = achievementService.getAchievementById(achievementId);

            verify(achievementRepository, times(1)).findById(achievementId);
            verify(achievementMapper, times(1)).toDto(achievement);
            assertEquals(achievementDto, achievementDtoResult);
        }
    }

    @Test
    @DisplayName("Should return list of UserAchievementDto when retrieving achievements by user ID")
    void getAchievementsByUserId() {
        when(userContext.getUserId()).thenReturn(userId);
        when(userAchievementRepository.findByUserId(userId)).thenReturn(List.of(userAchievement));
        when(userAchievementMapper.toDto(userAchievement)).thenReturn(userAchievementDto);

        List<UserAchievementDto> result = achievementService.getAchievementsByUserId();

        verify(userAchievementRepository).findByUserId(userId);
        verify(userAchievementMapper).toDto(userAchievement);
        assertNotNull(result);
        assertEquals(List.of(userAchievementDto), result);
    }

    @Test
    @DisplayName("testing getUserNotAttainedAchievements")
    void testGetUserNotAttainedAchievements() {
        when(userContext.getUserId()).thenReturn(userId);
        when(userAchievementRepository.findByUserId(userId)).thenReturn(List.of(userAchievement));
        when(achievementProgressRepository.findByUserId(userId)).thenReturn(List.of(achievementProgress));
        when(achievementProgressMapper.toDto(achievementProgress)).thenReturn(achievementProgressDto);

        List<AchievementProgressDto> userNotAttainedAchievements = achievementService.getUserNotAttainedAchievements();

        verify(userContext, times(1)).getUserId();
        assertEquals(1, userNotAttainedAchievements.size());
        assertIterableEquals(List.of(achievementProgressDto), userNotAttainedAchievements);
    }

    @Test
    @DisplayName("Should return list of AchievementProgressDto when retrieving achievement progress by user ID")
    void getAchievementProgressByUserId() {
        when(userContext.getUserId()).thenReturn(userId);
        when(achievementProgressRepository.findByUserId(userId)).thenReturn(List.of(achievementProgress));
        when(achievementProgressMapper.toDto(achievementProgress)).thenReturn(achievementProgressDto);

        List<AchievementProgressDto> result = achievementService.getAchievementProgressByUserId();

        verify(userContext, times(1)).getUserId();
        verify(achievementProgressRepository, times(1)).findByUserId(userId);
        verify(achievementProgressMapper, times(1)).toDto(achievementProgress);
        assertNotNull(result);
        assertIterableEquals(List.of(achievementProgressDto), result);
    }

    @Test
    @DisplayName("Should return AchievementDto when achievement is found in cache")
    void getAchievementByTitleFoundInCache() {
        when(achievementCache.getAchievementByTitle(achievementTitle)).thenReturn(achievement);
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        AchievementDto result = achievementService.getAchievementByTitle(achievementTitle);

        verify(achievementCache).getAchievementByTitle(achievementTitle);
        verify(achievementMapper).toDto(achievement);
        assertNotNull(result);
        assertEquals(achievementDto, result);
    }

    @Test
    @DisplayName("testing hasAchievement method")
    void testHasAchievement() {
        achievementService.hasAchievement(userId, achievementId);
        verify(userAchievementRepository, times(1))
                .existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    @DisplayName("testing createProgressIfNecessary with non appropriate value")
    void testCreateProgressIfNecessary() {
        achievementService.createProgressIfNecessary(userId, achievementId);
        verify(achievementProgressRepository, times(1))
                .createProgressIfNecessary(userId, achievementId);
    }


    @Test
    @DisplayName("testing getProgress with non appropriate value")
    void testGetProgressWithNonAppropriateValue() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> achievementService.getProgress(userId, achievementId));
    }

    @Test
    @DisplayName("testing getProgress with appropriate value")
    void testGetProgressWithAppropriateValue() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.of(achievementProgress));

        achievementService.getProgress(userId, achievementId);

        verify(achievementProgressRepository, times(1))
                .findByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    @DisplayName("testing giveAchievement method")
    void testGiveAchievement() {
        achievementService.giveAchievement(userId, achievement);
        verify(userAchievementRepository, times(1)).save(userAchievementArgumentCaptor.capture());
    }

    @Test
    @DisplayName("testing saveAchievementProgress method")
    void testSaveAchievementProgress() {
        achievementService.saveAchievementProgress(achievementProgress);
        verify(achievementProgressRepository, times(1)).save(achievementProgress);
    }

    @Test
    @DisplayName("Получение прогресса по достижению")
    void testGetProgress() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.of(achievementProgress));
        achievementService.getProgress(userId, achievementId);

        verify(achievementProgressRepository, times(1)).findByUserIdAndAchievementId(userId, achievementId);
    }
}