package faang.school.achievement.service;

import faang.school.achievement.client.UserServiceClient;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.dto.UserDto;
import faang.school.achievement.exception.NotFoundException;
import faang.school.achievement.mapper.UserAchievementMapperImpl;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.util.AchievementUtilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementUtilServiceTest {
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Spy
    private UserAchievementMapperImpl achievementMapper;
    @Mock
    private UserServiceClient userServiceClient;
    @InjectMocks
    private AchievementUtilService achievementUtilService;
    private long userId;
    private long achievementId;

    @BeforeEach
    void setUp() {
        userId = 1L;
        achievementId = 1L;
    }

    @Test
    public void testGetProgressExists() {
        AchievementProgress achievementProgress = initData(new AchievementProgress());
        AchievementProgress result = achievementUtilService.getProgress(userId, achievementId);
        assertEquals(achievementProgress, result);
    }

    @Test
    public void testGetProgressNotExists() {
        assertThrows(NotFoundException.class, () -> achievementUtilService.getProgress(userId, achievementId));
    }

    @Test
    public void testGetUserDto() {
        UserAchievementDto dto = new UserAchievementDto();
        dto.setUserId(userId);
        dto.setAchievementId(achievementId);
        Achievement achievement = new Achievement();
        achievement.setId(achievementId);
        UserAchievement userAchievement = new UserAchievement();
        when(achievementMapper.toEntity(any())).thenReturn(userAchievement);
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));
        achievementUtilService.giveAchievement(userId, achievementId);
        verify(userAchievementRepository).save(userAchievement);
    }

    @Test
    public void testGetAchievementFailure() {
        when(userServiceClient.getUser(userId)).thenReturn(new UserDto());
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> achievementUtilService.getProgress(userId, achievementId));
    }


    private AchievementProgress initData(AchievementProgress achievementProgress) {
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        Achievement achievement = new Achievement();
        achievement.setId(achievementId);
        if (achievementProgress != null) {
            achievementProgress.setUserId(userId);
            achievementProgress.setAchievement(achievement);
            when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.of(achievementProgress));
        } else {
            when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.empty());
        }
        when(userServiceClient.getUser(userId)).thenReturn(userDto);
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));

        return achievementProgress;
    }
}
