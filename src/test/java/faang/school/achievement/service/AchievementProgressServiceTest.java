package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementProgressServiceTest {

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @Mock
    private AchievementProgressMapper achievementProgressMapper;

    @InjectMocks
    private AchievementProgressService achievementProgressService;

    @Test
    void getAchievementInProgressForUserById_ShouldReturnAchievementProgressDtoList() {
        long userId = 1L;
        AchievementProgress achievementProgress1 = mock(AchievementProgress.class);
        AchievementProgress achievementProgress2 = mock(AchievementProgress.class);
        List<AchievementProgress> achievementProgressList = List.of(achievementProgress1, achievementProgress2);
        AchievementProgressDto achievementProgressDto1 = mock(AchievementProgressDto.class);
        AchievementProgressDto achievementProgressDto2 = mock(AchievementProgressDto.class);
        List<AchievementProgressDto> achievementProgressDtoList = List.of(achievementProgressDto1, achievementProgressDto2);

        when(achievementProgressRepository.findByUserId(userId)).thenReturn(achievementProgressList);
        when(achievementProgressMapper.toDto(achievementProgress1)).thenReturn(achievementProgressDto1);
        when(achievementProgressMapper.toDto(achievementProgress2)).thenReturn(achievementProgressDto2);

        List<AchievementProgressDto> result = achievementProgressService.getAchievementInProgressForUserById(userId);

        assertEquals(achievementProgressDtoList, result);
        verify(achievementProgressRepository, times(1)).findByUserId(userId);
        verify(achievementProgressMapper, times(1)).toDto(achievementProgress1);
        verify(achievementProgressMapper, times(1)).toDto(achievementProgress2);
    }

    @Test
    void getAchievementInProgressForUserById_ShouldReturnEmptyList_WhenNoAchievementsFound() {
        long userId = 2L;
        when(achievementProgressRepository.findByUserId(userId)).thenReturn(List.of());

        List<AchievementProgressDto> result = achievementProgressService.getAchievementInProgressForUserById(userId);

        assertEquals(List.of(), result);
        verify(achievementProgressRepository, times(1)).findByUserId(userId);
        verify(achievementProgressMapper, never()).toDto(any());
    }
}