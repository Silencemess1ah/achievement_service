package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/achievements")
@RequiredArgsConstructor
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping("users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserAchievementDto> getAllUserAchievements(@PathVariable long userId) {
        return achievementService.getAllUserAchievements(userId);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public AchievementDto getAchievementById(@PathVariable long id) {
        return achievementService.getAchievementById(id);
    }

    @GetMapping("/users/{userId}/progress")
    @ResponseStatus(HttpStatus.OK)
    public List<AchievementProgressDto> getAllUnreceivedUserAchievements(@PathVariable long userId) {
        return achievementService.getAllUnreceivedUserAchievements(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AchievementDto> getFilteredAchievements(@RequestBody AchievementFilterDto filter) {
        return achievementService.getFilteredAchievements(filter);
    }

}
