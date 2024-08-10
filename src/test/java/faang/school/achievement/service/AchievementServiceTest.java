package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private List<AchievementFilter> achievementFilters;

    @Mock
    private AchievementMapper achievementMapper;

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementProgressMapper achievementProgressMapper;

    @InjectMocks
    private AchievementService achievementService;

    @Test
    @DisplayName("testGetAchievementsWithFilter - Should apply filters and return filtered achievements")
    void testGetAchievementsWithFilter() {
        AchievementFilterDto filterDto = new AchievementFilterDto();
        List<Achievement> allAchievements = List.of(new Achievement(), new Achievement());
        List<AchievementDto> expectedDtos = List.of(new AchievementDto(), new AchievementDto());

        when(achievementRepository.findAll()).thenReturn(allAchievements);
        when(achievementFilters.stream()).thenReturn(Stream.of());
        when(achievementMapper.toDtos(anyList())).thenReturn(expectedDtos);

        List<AchievementDto> actualDtos = achievementService.getAchievementsWithFilter(filterDto);

        assertEquals(expectedDtos, actualDtos);
        verify(achievementRepository).findAll();
        verify(achievementMapper).toDtos(anyList());
    }

    @Test
    @DisplayName("testGetAllAchievementDtos - Should return all achievements as DTOs")
    void testGetAllAchievementDtos() {
        List<Achievement> allAchievements = List.of(new Achievement(), new Achievement());
        List<AchievementDto> expectedDtos = List.of(new AchievementDto(), new AchievementDto());

        when(achievementRepository.findAll()).thenReturn(allAchievements);
        when(achievementMapper.toDtos(allAchievements)).thenReturn(expectedDtos);

        List<AchievementDto> actualDtos = achievementService.getAllAchievementDtos();

        assertEquals(expectedDtos, actualDtos);
        verify(achievementRepository).findAll();
        verify(achievementMapper).toDtos(allAchievements);
    }

    @Test
    @DisplayName("testGetUserAchievements - Should return achievements of a specific user")
    void testGetUserAchievements() {
        Long userId = 1L;
        Achievement achievement1 = mock(Achievement.class);
        Achievement achievement2 = mock(Achievement.class);
        UserAchievement userAchievement1 = mock(UserAchievement.class);
        UserAchievement userAchievement2 = mock(UserAchievement.class);
        List<UserAchievement> userAchievements = List.of(userAchievement1, userAchievement2);
        List<AchievementDto> expectedDtos = List.of(new AchievementDto(), new AchievementDto());

        when(userAchievement1.getAchievement()).thenReturn(achievement1);
        when(userAchievement2.getAchievement()).thenReturn(achievement2);
        when(userAchievementRepository.findByUserId(userId)).thenReturn(userAchievements);
        when(achievementMapper.toDtos(List.of(achievement1, achievement2))).thenReturn(expectedDtos);

        List<AchievementDto> actualDtos = achievementService.getUserAchievements(userId);

        assertEquals(expectedDtos, actualDtos);
        verify(userAchievementRepository).findByUserId(userId);
        verify(achievementMapper).toDtos(List.of(achievement1, achievement2));
    }

    @Test
    @DisplayName("testGetAchievementById - Should return achievement DTO for given ID")
    void testGetAchievementById() {
        Long achievementId = 1L;
        Achievement achievement = new Achievement();
        AchievementDto expectedDto = new AchievementDto();

        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));
        when(achievementMapper.toDto(achievement)).thenReturn(expectedDto);

        AchievementDto actualDto = achievementService.getAchievementById(achievementId);

        assertEquals(expectedDto, actualDto);
        verify(achievementRepository).findById(achievementId);
        verify(achievementMapper).toDto(achievement);
    }

    @Test
    @DisplayName("testGetAchievementById - Should throw EntityNotFoundException if achievement not found")
    void testGetAchievementById_NotFound() {
        Long achievementId = 1L;

        when(achievementRepository.findById(achievementId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> achievementService.getAchievementById(achievementId));

        assertEquals(String.format("Achievement with id %d not found", achievementId), exception.getMessage());
        verify(achievementRepository).findById(achievementId);
    }

    @Test
    @DisplayName("testGetAchievementProgressByUserId - Should return user's achievement progress as DTOs")
    void testGetAchievementProgressByUserId() {
        Long userId = 1L;
        List<AchievementProgress> achievementProgresses = List.of(new AchievementProgress(), new AchievementProgress());
        List<AchievementProgressDto> expectedDtos = List.of(new AchievementProgressDto(), new AchievementProgressDto());

        when(achievementProgressRepository.findByUserId(userId)).thenReturn(achievementProgresses);
        when(achievementProgressMapper.toDtos(achievementProgresses)).thenReturn(expectedDtos);

        List<AchievementProgressDto> actualDtos = achievementService.getAchievementProgressByUserId(userId);

        assertEquals(expectedDtos, actualDtos);
        verify(achievementProgressRepository).findByUserId(userId);
        verify(achievementProgressMapper).toDtos(achievementProgresses);
    }
}