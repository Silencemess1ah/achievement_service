package faang.school.achievement.service.user_achievement;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAchievementServiceImplTest {

    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private UserAchievementMapper userAchievementMapper;

    @InjectMocks
    private UserAchievementServiceImpl userAchievementService;

    private final long userId = 1L;
    private UserAchievement userAchievement;
    private UserAchievementDto userAchievementDto;

    @BeforeEach
    void setUp() {
        Achievement achievement = Achievement.builder().id(2L).build();
        AchievementDto achievementDto = AchievementDto.builder().id(2L).build();

        userAchievement = UserAchievement.builder()
                .id(1L)
                .achievement(achievement)
                .userId(userId)
                .build();

        userAchievementDto = UserAchievementDto.builder()
                .id(1L)
                .achievement(achievementDto)
                .userId(userId)
                .build();
    }

    @Test
    void getAchievementsByUserId() {
        when(userAchievementRepository.findByUserId(userId)).thenReturn(List.of(userAchievement));
        when(userAchievementMapper.toDto(userAchievement)).thenReturn(userAchievementDto);

        List<UserAchievementDto> actual = userAchievementService.getAchievementsByUserId(userId);
        assertIterableEquals(List.of(userAchievementDto), actual);

        InOrder inOrder = inOrder(userAchievementRepository, userAchievementMapper);
        inOrder.verify(userAchievementRepository).findByUserId(userId);
        inOrder.verify(userAchievementMapper).toDto(userAchievement);
    }
}