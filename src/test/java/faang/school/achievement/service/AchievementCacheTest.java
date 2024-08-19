package faang.school.achievement.service;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.validator.AchievementValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementCacheTest {

    @Mock
    private Map<String, AchievementDto> achievements;

    @Mock
    public AchievementRepository achievementRepository;

    @Spy
    public AchievementMapper achievementMapper = Mappers.getMapper(AchievementMapper.class);

    @Mock
    public AchievementValidator achievementValidator;

    @InjectMocks
    public AchievementCache achievementCache;

    private AchievementDto achievementDto;
    private List<AchievementDto> achievementDtoList;
    private Map<String, AchievementDto> achievementsMap;
    private String title;

    @BeforeEach
    public void init() {
        achievementDto = AchievementDto.builder()
                .title("Achievement 1")
                .build();

        achievementDtoList = Collections.singletonList(achievementDto);

        achievementsMap = new HashMap<>();
        achievementsMap.put("Achievement 1", achievementDto);
        title = "Achievement 1";
    }

    @Test
    public void testInit() {
        when(achievementRepository.findAll()).thenReturn(Collections.singletonList(new Achievement()));
        when(achievementMapper.toDtoList(anyList())).thenReturn(achievementDtoList);

        achievementCache.init();

        verify(achievementRepository, times(1)).findAll();
        verify(achievementMapper, times(1)).toDtoList(anyList());
        verify(achievements, times(1)).putAll(achievementsMap);
    }

    @Test
    public void testGetAchievementDto() {
        when(achievements.get(title)).thenReturn(achievementDto);

        AchievementDto result = achievementCache.get(title);

        verify(achievementValidator, times(1)).checkTitle(title);
        assertEquals(achievementDto, result);
    }
}
