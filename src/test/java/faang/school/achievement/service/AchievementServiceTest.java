package faang.school.achievement.service;

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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @Mock
    AchievementRepository achievementRepository;

    @Mock
    AchievementMapper achievementMapper;

    @Mock
    AchievementCache achievementCache;

    @InjectMocks
    AchievementService achievementService;

    String achievementTitle;
    Achievement achievement;
    AchievementDto achievementDto;


    @BeforeEach
    void setUp() {
        achievementTitle = "title";
        achievement = new Achievement();
        achievementDto = AchievementDto.builder().build();
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
        verify(achievementMapper).toDto(achievement);
        assertNotNull(result);
        assertEquals(achievementDto, result);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when achievement is not found in cache or repository")
    void getAchievementByTitle_NotFound() {
        when(achievementCache.getAchievementByTitle(achievementTitle)).thenReturn(Optional.empty());
        when(achievementRepository.findByTitle(achievementTitle)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> achievementService.getAchievementByTitle(achievementTitle));
    }
}