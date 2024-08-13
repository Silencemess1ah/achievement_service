package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievement")
@Validated
public class AchievementController {
    private final AchievementService achievementService;
    private final UserAchievementService userAchievementService;
    private final AchievementProgressService achievementProgressService;

    @GetMapping("/filter")
    public List<AchievementDto> getAchievementByFilter(@RequestBody AchievementFilterDto filter) {
        return achievementService.getAchievementByFilter(filter);
    }

    @GetMapping("/all")
    public List<AchievementDto> getAllAchievement() {
        return achievementService.getAllAchievement();
    }

    @GetMapping("completed/{userId}")
    public List<UserAchievementDto> getAchievementFinishedForUserById(@PathVariable("userId") Long userId) {
        return userAchievementService.getAchievementFinishedForUserById(userId);
    }

    @GetMapping("/{id}")
    public AchievementDto getAchievementById(@PathVariable("id") Long id) {
        return achievementService.getAchievementById(id);
    }

    @GetMapping("progress/{userId}")
    public List<AchievementProgressDto> getAchievementInProgressForUserById(@PathVariable("userId") Long userId) {
        return achievementProgressService.getAchievementInProgressForUserById(userId);
    }
}
