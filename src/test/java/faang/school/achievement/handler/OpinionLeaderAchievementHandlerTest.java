package faang.school.achievement.handler;

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
