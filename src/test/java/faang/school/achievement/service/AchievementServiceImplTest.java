package faang.school.achievement.service;


import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.cache.CacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceImplTest {

    @InjectMocks
    private AchievementServiceImpl service;

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @Mock
    private CacheService<Achievement> cacheService;

    @Test
    public void testHasAchievement() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(1L, 1L))
                .thenReturn(Boolean.TRUE);

        assertEquals(true, service.hasAchievement(1L, 1L));
    }

    @Test
    public void testGetProgress() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(1L, 1L))
                .thenReturn(Optional.of(new AchievementProgress()));

        assertEquals(new AchievementProgress(), service.getProgress(1L, 1L));
    }

    @Test
    public void testCreateProgressIfNecessary() {
        service.createProgressIfNecessary(1L, 1L);

        verify(achievementProgressRepository, times(1))
                .createProgressIfNecessary(1L, 1L);
    }

    @Test
    public void testGiveAchievement() {
        Achievement achievement = new Achievement();
        UserAchievement userAchievement = new UserAchievement();

        when(userAchievementRepository.save(any(UserAchievement.class))).thenReturn(userAchievement);
        service.giveAchievement(1L, achievement);
        verify(userAchievementRepository, times(1)).save(any(UserAchievement.class));
    }

    @Test
    public void testUploadAchievement() {
        List<Achievement> achievementList = new ArrayList<>();
        Achievement achievement1 = new Achievement();
        achievement1.setTitle("title1");
        achievementList.add(achievement1);

        when(achievementRepository.findAll()).thenReturn(achievementList);
        doNothing().when(cacheService).setCacheValue(achievement1.getTitle(), achievement1);

        service.uploadAchievement();

        verify(cacheService, times(1)).setCacheValue(achievement1.getTitle(), achievement1);
    }
}
