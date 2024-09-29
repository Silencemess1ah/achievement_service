package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.SortField;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.exception.BadRequestException;
import faang.school.achievement.exception.ResourceNotFoundException;
import faang.school.achievement.filter.achievement.AchievementDescriptionFilter;
import faang.school.achievement.filter.achievement.AchievementFilter;
import faang.school.achievement.filter.achievement.AchievementRarityFilter;
import faang.school.achievement.filter.achievement.AchievementTitleFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private AchievementProgressMapper achievementProgressMapper;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private AchievementMapper achievementMapper;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private UserAchievementMapper userAchievementMapper;

//    @InjectMocks
    private AchievementService achievementService;

    @Mock
    private AchievementDescriptionFilter descriptionFilter;

    @Mock
    private AchievementRarityFilter rarelyFilter;

    @Mock
    private AchievementTitleFilter titleFilter;
    private Achievement achievement;
    private List<Achievement> achievementsList;
    private AchievementDto achievementDto;
    private List<AchievementDto> achievementDtoList;
    private AchievementFilterDto achievementFilterDto;
    private List<AchievementFilter> achievementFilters;
    private AchievementProgress achievementProgress;
    private AchievementProgressDto achievementProgressDto;
    private UserAchievement userAchievement;
    private UserAchievementDto userAchievementDto;

    @BeforeEach
    void setUp() {
        achievement = Achievement.builder().id(1L).build();
        achievementDto = AchievementDto.builder().build();
        achievementsList = List.of(achievement);
        achievementDtoList = List.of(achievementDto);
        achievementService = new AchievementService(
                achievementProgressRepository,
                achievementProgressMapper,
                achievementRepository,
                achievementMapper,
                achievementFilters,
                userAchievementRepository,
                userAchievementMapper
        );

        achievementFilters = List.of(descriptionFilter, rarelyFilter, titleFilter);

        achievementFilterDto = AchievementFilterDto.
                builder()
                .description("description")
                .page(0)
                .size(10)
                .rarity("rarity")
                .title("title")
                .sortField(SortField.CREATED_AT)
                .direction(Sort.Direction.ASC)
                .build();

        achievementProgress = AchievementProgress.builder().id(1L).build();
        achievementProgressDto = AchievementProgressDto.builder().id(1L).build();
        userAchievement = UserAchievement.builder().id(1L).userId(1L).build();
        userAchievementDto = UserAchievementDto.builder().id(1L).userId(1L).build();

        achievementService = new AchievementService(achievementProgressRepository, achievementProgressMapper, achievementRepository, achievementMapper, achievementFilters ,userAchievementRepository, userAchievementMapper);
    }

    @Test
    @DisplayName("Получение всех достижений с фильтрацией: тест успешного выполнения")
    void testGetAchievements() {

        when(achievementRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(achievementsList));
        //when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);
        when(descriptionFilter.isApplicable(any(AchievementFilterDto.class))).thenReturn(true);
        when(rarelyFilter.isApplicable(any(AchievementFilterDto.class))).thenReturn(true);
        when(titleFilter.isApplicable(any(AchievementFilterDto.class))).thenReturn(true);
        when(descriptionFilter.apply(any(Stream.class), any(AchievementFilterDto.class))).thenReturn(achievementsList.stream());
        when(rarelyFilter.apply(any(Stream.class), any(AchievementFilterDto.class))).thenReturn(achievementsList.stream());
        when(titleFilter.apply(any(Stream.class), any(AchievementFilterDto.class))).thenReturn(achievementsList.stream());
        when(achievementMapper.toDto(any(Achievement.class))).thenReturn(achievementDto);

        List<AchievementDto> allByFilter = achievementService.getAchievements(achievementFilterDto);

        verify(achievementRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Получение достижения по ID: тест успешного выполнения")
    void testGetAchievementById() {
        long id = 1L;
        Achievement achievement = new Achievement();
        AchievementDto achievementDto = new AchievementDto();

        when(achievementRepository.findById(id)).thenReturn(Optional.of(achievement));
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        AchievementDto result = achievementService.getAchievementById(id);

        assertEquals(achievementDto, result);
    }

    @Test
    @DisplayName("Получение достижения по ID: тест на случай отсутствия достижения")
    void testGetAchievementById_NotFound() {
        long id = 1L;

        when(achievementRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> achievementService.getAchievementById(id));
    }

    @Test
    @DisplayName("Получение достижений пользователя: тест успешного выполнения")
    void testGetUserAchievements() {
        long userId = 1L;
        UserAchievement userAchievement = new UserAchievement();
        UserAchievementDto userAchievementDto = new UserAchievementDto();

        when(userAchievementRepository.findByUserId(userId)).thenReturn(List.of(userAchievement));
        when(userAchievementMapper.toListDto(List.of(userAchievement))).thenReturn(List.of(userAchievementDto));

        List<UserAchievementDto> result = achievementService.getUserAchievements(userId);

        assertEquals(1, result.size());
        assertEquals(userAchievementDto, result.get(0));
    }

    @Test
    @DisplayName("Получение достижений пользователя в процессе выполнения: тест успешного выполнения")
    void testGetUserAchievementsInProgress() {
        long userId = 1L;
        AchievementProgress achievementProgress = new AchievementProgress();
        AchievementProgressDto achievementProgressDto = new AchievementProgressDto();

        when(achievementProgressRepository.findByUserId(userId)).thenReturn(List.of(achievementProgress));
        when(achievementProgressMapper.toListDto(List.of(achievementProgress))).thenReturn(List.of(achievementProgressDto));

        List<AchievementProgressDto> result = achievementService.getUserAchievementsInProgress(userId);

        assertEquals(1, result.size());
        assertEquals(achievementProgressDto, result.get(0));
    }

    @Test
    @DisplayName("Узнать, существует ли достижение у пользователя: тест успешного выполнения, когда есть достижение")
    public void testHasAchievementReturnsTrue() {
        long userId = 1L;
        long achievementId = 2L;
        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(true);

        boolean result = achievementService.hasAchievement(userId, achievementId);

        assertTrue(result);
        verify(userAchievementRepository).existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    @DisplayName("Узнать, существует ли достижение у пользователя: тест успешного выполнения, когда нет достижения")
    public void testHasAchievementReturnsFalse() {
        long userId = 1L;
        long achievementId = 2L;
        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(false);

        boolean result = achievementService.hasAchievement(userId, achievementId);

        assertFalse(result);
        verify(userAchievementRepository).existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    @DisplayName("Получение прогресса пользователя: тест успешного выполнения")
    void testGetAchievementProgress() {
        long userId = 1L;
        long achievementId = 1L;
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.of(achievementProgress));
        when(achievementProgressMapper.toDto(achievementProgress)).thenReturn(achievementProgressDto);

        AchievementProgressDto actual = achievementService.getAchievementProgress(userId, achievementId);

        assertNotNull(actual);
        assertEquals(achievementProgressDto, actual);
    }

    @Test
    @DisplayName("Получение прогресса пользователя: тест на случай, если у пользователя нет прогресса по достижению")
    void testGetAchievementProgressResourceNotFoundException() {
        long userId = 1L;
        long achievementId = 1L;
        doThrow(ResourceNotFoundException.class).when(achievementProgressRepository).findByUserIdAndAchievementId(userId, achievementId);

        assertThrows(ResourceNotFoundException.class, () -> achievementService.getAchievementProgress(userId, achievementId));
    }

    @Test
    @DisplayName("Сохранения прогресса пользователя: тест успешного выполнения")
    void testSaveAchievementProgress() {
        when(achievementProgressMapper.toEntity(achievementProgressDto)).thenReturn(achievementProgress);
        when(achievementProgressRepository.save(achievementProgress)).thenReturn(achievementProgress);
        when(achievementProgressMapper.toDto(achievementProgress)).thenReturn(achievementProgressDto);

        AchievementProgressDto result = achievementService.saveAchievementProgress(achievementProgressDto);

        assertNotNull(result);
        assertEquals(achievementProgressDto, result);
        verify(achievementProgressMapper).toEntity(achievementProgressDto);
        verify(achievementProgressRepository).save(achievementProgress);
        verify(achievementProgressMapper).toDto(achievementProgress);
    }

    @Test
    @DisplayName("Создание прогресса достижения, если необходимо: тест успешного выполнения")
    void testCreateProgressIfNecessary() {
        long userId = 1L;
        long achievementId = 2L;

        achievementService.createProgressIfNecessary(userId, achievementId);

        verify(achievementProgressRepository).createProgressIfNecessary(userId, achievementId);
    }

    @Test
    @DisplayName("Создать полученное достижение пользователем: тест успешного выполнения")
    public void testGiveAchievement() {
        long userId = 1L;
        when(achievementMapper.toEntity(achievementDto)).thenReturn(achievement);
        when(userAchievementRepository.save(any(UserAchievement.class))).thenReturn(userAchievement);
        when(userAchievementMapper.toDto(userAchievement)).thenReturn(userAchievementDto);

        UserAchievementDto result = achievementService.giveAchievement(achievementDto, userId);

        assertNotNull(result);
        assertEquals(userAchievementDto, result);
        verify(achievementMapper).toEntity(achievementDto);
        verify(userAchievementRepository).save(any(UserAchievement.class));
        verify(userAchievementMapper).toDto(any(UserAchievement.class));
    }

}