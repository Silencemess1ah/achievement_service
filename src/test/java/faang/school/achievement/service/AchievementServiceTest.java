package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.exception.ResourceNotFoundException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.filter.AchievementFilter;
import faang.school.achievement.service.filter.DescriptionFilter;
import faang.school.achievement.service.filter.RarelyFilter;
import faang.school.achievement.service.filter.TitleFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    @Mock
    public AchievementRepository achievementRepository;

    @Mock
    public UserAchievementRepository userAchievementRepository;

    @Mock
    public AchievementProgressRepository achievementProgressRepository;

    @Spy
    public AchievementMapper achievementMapper = Mappers.getMapper(AchievementMapper.class);

    @InjectMocks
    public AchievementProgressMapper achievementProgressMapper = Mappers.getMapper(AchievementProgressMapper.class);

    @InjectMocks
    public UserAchievementMapper userAchievementMapper = Mappers.getMapper(UserAchievementMapper.class);

    public AchievementService achievementService;

    private Achievement achievement;
    private List<Achievement> achievementsList;
    private AchievementDto achievementDto;
    private List<AchievementDto> achievementDtoList;
    private AchievementFilterDto achievementFilterDto;
    private List<AchievementFilter> achievementFilters;
    private UserAchievement userAchievement;
    private List<UserAchievement> userAchievements;
    private UserAchievementDto userAchievementDto;
    private List<UserAchievementDto> userAchievementDtoList;
    private AchievementProgress achievementProgress;
    private AchievementProgressDto achievementProgressDto;
    private List<AchievementProgress> achievementProgressList;
    private List<AchievementProgressDto> achievementProgressDtoList;

    private Long userId;
    private Long achievementId;

    @Mock
    private DescriptionFilter descriptionFilter;

    @Mock
    private RarelyFilter rarelyFilter;

    @Mock
    private TitleFilter titleFilter;

    @BeforeEach
    public void init() {
        achievement = Achievement.builder().id(1L).build();
        achievementDto = AchievementDto.builder().id(1L).build();
        achievementsList = List.of(achievement);
        achievementDtoList = List.of(achievementDto);
        achievementFilterDto = AchievementFilterDto.
                builder()
                .descriptionPattern("description")
                .rarityPattern("rarity")
                .titlePattern("title")
                .build();
        achievementFilters = List.of(descriptionFilter, rarelyFilter, titleFilter);

        achievementService = new AchievementService(achievementRepository,
                userAchievementRepository,
                achievementProgressRepository,
                achievementFilters,
                achievementMapper,
                achievementProgressMapper,
                userAchievementMapper);

        userAchievement = new UserAchievement();
        userAchievementDto = new UserAchievementDto();
        userAchievements = List.of(userAchievement);
        userAchievementDtoList = List.of(userAchievementDto);

        achievementProgress = new AchievementProgress();
        achievementProgressDto = new AchievementProgressDto();
        achievementProgressList = List.of(achievementProgress);
        achievementProgressDtoList = List.of(achievementProgressDto);

        userId = 1L;
        achievementId = 1L;
    }

    @Test
    void testGetAllByFilter() {
        when(achievementRepository.findAll()).thenReturn(achievementsList);
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);
        when(descriptionFilter.isApplicable(any(AchievementFilterDto.class))).thenReturn(true);
        when(rarelyFilter.isApplicable(any(AchievementFilterDto.class))).thenReturn(true);
        when(titleFilter.isApplicable(any(AchievementFilterDto.class))).thenReturn(true);
        when(descriptionFilter.apply(any(Stream.class), any(AchievementFilterDto.class))).thenReturn(Stream.of(AchievementDto.class));

        List<AchievementDto> allByFilter = achievementService.getAllByFilter(achievementFilterDto);

        assertEquals(achievementDtoList.size(), allByFilter.size());
        verify(achievementRepository, times(1)).findAll();
    }

    @Test
    void testGetByUserIdThrowsException() {
        when(userAchievementRepository.findByUserId(userId)).thenReturn(userAchievements);

        List<UserAchievementDto> result = achievementService.getByUserId(userId);

        assertEquals(userAchievementDtoList.size(), result.size());
        verify(userAchievementRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetByIdNotFound() {
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            achievementService.getById(achievementId);
        });

        assertEquals(ResourceNotFoundException.class, exception.getClass());
        verify(achievementRepository, times(1)).findById(achievementId);
    }

    @Test
    void testGetByIdSuccessfully() {
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        AchievementDto result = achievementService.getById(achievementId);

        assertEquals(achievementDto, result);
        verify(achievementRepository, times(1)).findById(achievementId);
        verify(achievementMapper, times(1)).toDto(achievement);
    }

    @Test
    void testEntityGetByIdNotFound() {
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            achievementService.getEntityById(achievementId);
        });

        assertEquals(ResourceNotFoundException.class, exception.getClass());
        verify(achievementRepository, times(1)).findById(achievementId);
    }

    @Test
    void testGetEntityByIdSuccessfully() {
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));


        Achievement result = achievementService.getEntityById(achievementId);

        assertEquals(achievement, result);
        verify(achievementRepository, times(1)).findById(achievementId);
    }

    @Test
    void testGetAchievementInProgress() {
        when(achievementProgressRepository.findByUserId(userId)).thenReturn(achievementProgressList);

        List<AchievementProgressDto> result = achievementService.getAchievementInProgress(userId);

        assertEquals(1, result.size());
        verify(achievementProgressRepository, times(1)).findByUserId(userId);
    }












}
