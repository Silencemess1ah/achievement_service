package faang.school.achievement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.util.AchievementTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AchievementControllerTest {
    @InjectMocks
    private AchievementController controller;
    @Mock
    private AchievementService service;

    private AchievementTestContainer container;
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        container = new AchievementTestContainer();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getUserAchievements() throws Exception {
        // given
        String uri = "/achievements/all";
        AchievementFilterDto requestFilters = container.filters();
        List<AchievementDto> responseList = prepareDtoList();
        System.out.println(responseList);

        when(service.getAchievements(requestFilters)).thenReturn(responseList);

        // then
        mockMvc.perform(get(uri)
                        .content(objectMapper.writeValueAsString(requestFilters))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(responseList.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(responseList.get(1).getId()));
    }

    @Test
    void testGetUserAchievements() throws Exception {
        // given
        String uri = "/achievements/user_achievements/{user_id}";
        Long userId = container.userId();
        AchievementDto dto = container.achievementDto();
        List<AchievementDto> responseList = List.of(dto);
        System.out.println(responseList);

        when(service.getUserAchievements(userId)).thenReturn(responseList);

        // then
        mockMvc.perform(get(uri, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]").value(dto));
    }

    @Test
    void getAchievement() throws Exception {
        // given
        String uri = "/achievements/{achievement_id}";
        long achievementId = container.achievementId();
        AchievementDto responseDto = AchievementDto.builder()
                .id(achievementId)
                .title(container.title())
                .build();
        when(service.getAchievement(achievementId)).thenReturn(responseDto);

        // then
        mockMvc.perform(get(uri, achievementId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(responseDto));
    }

    @Test
    void getAchievementsProgress() throws Exception {
        // given
        String uri = "/achievements/achievements_in_progress/{user_id}";
        Long userId = container.userId();
        AchievementProgressDto dto = container.achievementProgresDto();
        List<AchievementProgressDto> responseList = List.of(dto);
        System.out.println(responseList);

        when(service.getAchievementsProgress(userId)).thenReturn(responseList);

        // then
        mockMvc.perform(get(uri, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]").value(dto));
    }

    private List<AchievementDto> prepareDtoList() {
        AchievementDto firstDto = container.achievementDto();
        AchievementDto secondDto = container.achievementDto();

        return List.of(firstDto, secondDto);
    }
}