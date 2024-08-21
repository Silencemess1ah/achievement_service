package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievements")
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping
    public List<AchievementDto> getAchievementsByFilter(@RequestBody AchievementFilterDto achievementFilterDto) {
        return achievementService.getAchievementsByFilter(achievementFilterDto);
    }

    @GetMapping("/users")
    public List<UserAchievementDto> getAchievementsByUsedId() {
        return achievementService.getAchievementsByUserId();
    }

    @GetMapping("/{id}")
    public AchievementDto getAchievementById(@PathVariable long id) {
        return achievementService.getAchievementById(id);
    }

    @GetMapping("/users/progress")
    public List<AchievementProgressDto> getAchievementProgressByUserId() {
        return achievementService.getAchievementProgressByUserId();
    }
    
    @GetMapping("/title")
    public AchievementDto getAchievementByTitle(@RequestParam String title) {
        return achievementService.getAchievementByTitle(title);
    }
}
