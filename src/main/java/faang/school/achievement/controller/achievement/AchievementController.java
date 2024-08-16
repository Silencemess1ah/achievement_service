package faang.school.achievement.controller.achievement;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.service.achievement.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@Tag(name = "Achievements", description = "Achievement handler")
@RequestMapping("/achievements")
public class AchievementController {
    private final AchievementService achievementService;

    @Operation(summary = "Get achievements by filter")
    @PostMapping("/getByFilter")
    public List<AchievementDto> getAchievementsByFilter(@RequestBody AchievementFilterDto filter) {
        return achievementService.getAchievementsByFilter(filter);
    }

    @Operation(summary = "Get all user's achievements by user id")
    @GetMapping("/userAchievements")
    public List<UserAchievementDto> getUserAchievementsByUserId(@RequestHeader("x-user-id") String userId) {
        return achievementService.getUserAchievementsByUserId();
    }

    @Operation(summary = "Get achievement by id")
    @GetMapping("/{achievementId}")
    public AchievementDto getAchievementById(@PathVariable
                                             @NotNull(message = "Achievement id cannot be null")
                                             @Min(value = 1L, message = "Achievement id cannot be less than 1")
                                             Long achievementId) {
        return achievementService.getAchievementById(achievementId);
    }

    @Operation(summary = "Get all user's in progress achievements by user id")
    @GetMapping("/inProgress")
    public List<AchievementProgressDto> getInProgressUserAchievementsByUserId(@RequestHeader("x-user-id") String userId) {
        return achievementService.getInProgressUserAchievementsByUserId();
    }
}
