package faang.school.achievement.dto.handler2;

import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OpinionLeaderAchievementHandlerTest {
    @InjectMocks
    private OpinionLeaderAchievementHandler opinionLeaderAchievementHandler;

    @Mock
    private AchievementService achievementService;


}
