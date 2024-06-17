package faang.school.achievement.controller;

import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.service.user_achievement.UserAchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/achievements/user")
@RequiredArgsConstructor
public class UserAchievementController {

    private final UserAchievementService userAchievementService;

    @GetMapping("/{userId}")
    public List<UserAchievementDto> getAchievementsByUserId(@PathVariable long userId) {
        return userAchievementService.getAchievementsByUserId(userId);
    }
}
