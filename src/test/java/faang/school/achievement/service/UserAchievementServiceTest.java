package faang.school.achievement.service;

import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.DisplayName;
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
class UserAchievementServiceTest {

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private UserAchievementMapper userAchievementMapper;

    @InjectMocks
    private UserAchievementService userAchievementService;

    @DisplayName("Should return UserAchievementDto list for finished achievements by user id")
    @Test
    void getAchievementFinishedForUserById_ShouldReturnUserAchievementDtoList() {
        long userId = 1L;
        UserAchievement userAchievement1 = mock(UserAchievement.class);
        UserAchievement userAchievement2 = mock(UserAchievement.class);
        List<UserAchievement> userAchievementList = List.of(userAchievement1, userAchievement2);

        UserAchievementDto userAchievementDto1 = mock(UserAchievementDto.class);
        UserAchievementDto userAchievementDto2 = mock(UserAchievementDto.class);
        List<UserAchievementDto> userAchievementDtoList = List.of(userAchievementDto1, userAchievementDto2);

        when(userAchievementRepository.findByUserId(userId)).thenReturn(userAchievementList);
        when(userAchievementMapper.toDto(userAchievement1)).thenReturn(userAchievementDto1);
        when(userAchievementMapper.toDto(userAchievement2)).thenReturn(userAchievementDto2);

        List<UserAchievementDto> result = userAchievementService.getAchievementFinishedForUserById(userId);

        assertEquals(userAchievementDtoList, result);
        verify(userAchievementRepository, times(1)).findByUserId(userId);
        verify(userAchievementMapper, times(1)).toDto(userAchievement1);
        verify(userAchievementMapper, times(1)).toDto(userAchievement2);
    }

    @DisplayName("Should return empty list when no finished achievements are found for user by id")
    @Test
    void getAchievementFinishedForUserById_ShouldReturnEmptyList_WhenNoAchievementsFound() {
        long userId = 2L;
        when(userAchievementRepository.findByUserId(userId)).thenReturn(List.of());

        List<UserAchievementDto> result = userAchievementService.getAchievementFinishedForUserById(userId);

        assertEquals(List.of(), result);
        verify(userAchievementRepository, times(1)).findByUserId(userId);
        verify(userAchievementMapper, never()).toDto(any());
    }
}