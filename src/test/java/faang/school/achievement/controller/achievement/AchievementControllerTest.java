package faang.school.achievement.controller.achievement;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.service.achievement.AchievementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementControllerTest {
    @Mock
    private AchievementService achievementService;

    @InjectMocks
    private AchievementController achievementController;

    @Test
    void testGetAchievementsByFilter() {
        AchievementFilterDto filterDto = new AchievementFilterDto();
        List<AchievementDto> expectedResponse = Collections.singletonList(new AchievementDto());

        when(achievementService.getAchievementsByFilter(any(AchievementFilterDto.class)))
                .thenReturn(expectedResponse);

        List<AchievementDto> actualResponse = achievementController.getAchievementsByFilter(filterDto);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetUserAchievementsByUserId() {
        String userId = "123";
        List<UserAchievementDto> expectedResponse = Collections.singletonList(new UserAchievementDto());

        when(achievementService.getUserAchievementsByUserId())
                .thenReturn(expectedResponse);

        List<UserAchievementDto> actualResponse = achievementController.getUserAchievementsByUserId(userId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetAchievementById() {
        long achievementId = 1L;
        AchievementDto expectedResponse = new AchievementDto();

        when(achievementService.getAchievementById(achievementId))
                .thenReturn(expectedResponse);

        AchievementDto actualResponse = achievementController.getAchievementById(achievementId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetInProgressUserAchievementsByUserId() {
        String userId = "123";
        List<AchievementProgressDto> expectedResponse = Collections.singletonList(new AchievementProgressDto());

        when(achievementService.getInProgressUserAchievementsByUserId())
                .thenReturn(expectedResponse);

        List<AchievementProgressDto> actualResponse = achievementController.getInProgressUserAchievementsByUserId(userId);

        assertEquals(expectedResponse, actualResponse);
    }
}
