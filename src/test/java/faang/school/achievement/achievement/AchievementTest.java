package faang.school.achievement.achievement;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.filter.AchievementDescriptionFilter;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.config.context.UserContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AchievementTest {

    @InjectMocks
    private AchievementService achievementService;
    @Mock
    private UserContext userContext;
    @Mock
    private AchievementMapper achievementMapper;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private AchievementDescriptionFilter achievementDescriptionFilter;
    @Mock
    private List<AchievementFilter> achievementFiltersList;

    private AchievementDto achievementDto;
    private Achievement achievement;
    private Iterable<Achievement> achievements;
    private AchievementFilterDto achievementFilterDto;
    private List<AchievementDto> achievementDtoList;
    private List<Achievement> achievementList;

    @BeforeEach
    void setUp() {
        achievementDto = new AchievementDto(1L, "title", Rarity.COMMON);
        achievement = new Achievement(1L, "title", "desc", Rarity.COMMON,
                List.of(), List.of(), 1L, LocalDateTime.now(), LocalDateTime.now());

        achievementDtoList = List.of(achievementDto);

        achievementList = new ArrayList<>();
        achievementList.add(achievement);
        achievementList.add(achievement);

        achievements = achievementList;

        achievementDescriptionFilter = new AchievementDescriptionFilter();
        achievementFilterDto = new AchievementFilterDto();
    }

    @Test
    void getAllAchievement() {
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);
        when(achievementRepository.findAll()).thenReturn((List<Achievement>) achievements);

        achievementService.getAllAchievement(achievementFilterDto);

        assertEquals(1L, achievementDto.getId());
    }

    @Test
    void getAllAchievementForUser() {
        when(achievementRepository.findAllAchievementForUser(1L)).thenReturn(achievementList);
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        List<AchievementDto> result = achievementService.getAllAchievementForUser(1L);

        assertEquals(achievementDtoList.get(0).getId(), result.get(0).getId());
    }

    @Test
    void getAchievement() {
        when(achievementRepository.findById(1L)).thenReturn(Optional.ofNullable(achievement));
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        List<AchievementDto> result = achievementService.getAchievement(1L);

        assertEquals(achievementDtoList.get(0).getId(), result.get(0).getId());
    }

    @Test
    public void getNoAchievementTest() {
        AchievementProgress achievementProgress1 = new AchievementProgress();
        achievementProgress1.setAchievement(new Achievement());
        AchievementProgress achievementProgress2 = new AchievementProgress();
        achievementProgress2.setAchievement(new Achievement());

        when(achievementProgressRepository.findByUserId(1L))
                .thenReturn(List.of(achievementProgress1, achievementProgress2));

        AchievementDto achievementDto1 = new AchievementDto();
        AchievementDto achievementDto2 = new AchievementDto();

        when(achievementMapper.toDto(achievementProgress1.getAchievement()))
                .thenReturn(achievementDto1);
        when(achievementMapper.toDto(achievementProgress2.getAchievement()))
                .thenReturn(achievementDto2);

        List<AchievementDto> result = achievementService.getNoAchievement(1L);

        assertEquals(2, result.size());
        assertEquals(achievementDto1, result.get(0));
        assertEquals(achievementDto2, result.get(1));
    }
}