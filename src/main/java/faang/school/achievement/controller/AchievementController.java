package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Evgenii Malkov
 */
@RestController()
@RequestMapping("/achievements")
@RequiredArgsConstructor
public class AchievementController {
    private final AchievementService achievementService;
    private final UserAchievementService userAchievementService;
    private final AchievementProgressService achievementProgressService;

    @GetMapping("/filter")
    public Page<AchievementDto> getAchievementByFilter(@RequestBody AchievementFilterDto filter,
                                                       @RequestBody Pageable pageable) {
        return achievementService.getAchievementByFilter(filter, pageable);
    }

    @GetMapping("{id}")
    public AchievementDto getAchievementById(@PathVariable long id) {
    @GetMapping("/all")
    public Page<AchievementDto> getAllAchievement(@RequestBody Pageable pageable) {
        return achievementService.getPageableAchievements(pageable);
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
    @GetMapping("/all")
    public List<AchievementDto> getAchievements(AchievementFilterDto filter) {
        return achievementService.getAchievements(filter);
    }

    @GetMapping("user/{id}")
    public List<UserAchievementDto> getCurrentUserAchievements(@PathVariable long id) {
        return achievementService.getUserAchievements(id);
    }

    @GetMapping("user/{id}/progress")
    public List<AchievementProgressDto> getCurrentUserAchievementsInProgress(@PathVariable long id) {
        return achievementService.getUserAchievementsInProgress(id);
    }
}
