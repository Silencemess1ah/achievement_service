package faang.school.achievement.controller;

import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/achievement")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    // Just a test method for event publishing. You can delete it freely.
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void giveAchievement(@RequestParam("user-id") Long userId,
                                @RequestParam("title") String achievementTitle) {
        achievementService.giveAchievement(userId, achievementTitle);
    }
}
