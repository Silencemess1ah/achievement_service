package faang.school.achievement.util;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;

import java.time.LocalDateTime;
import java.util.List;

public class AchievementTestContainer {
    private long id;
    private long achievementId;
    private String title;
    private String description;
    private Rarity rarity;
    private List<UserAchievement> userAchievements;
    private List<AchievementProgress> progresses;
    private long points;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private long userId;
    private long version;

    public AchievementTestContainer() {
        id = 0L;
        achievementId = ++id;
        title = "title";
        description = "description";
        rarity = Rarity.COMMON;
        userAchievements = createUserAchievements();
        progresses = createProgresses();
        points = ++id;
        createdAt = LocalDateTime.now();
        updatedAt = createdAt.plusDays(1);

        userId = ++id;
        version = ++id;
    }

    public long achievementId() {
        return achievementId;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public Rarity rarity() {
        return rarity;
    }

    public Long userId() {
        return userId;
    }

    public AchievementProgress achievementProgress() {
        return new AchievementProgress(++id, achievement(), userId, points, createdAt, updatedAt, version);
    }

    public Achievement achievement() {
        return new Achievement(++id, title, description, rarity, userAchievements, progresses, points,
                createdAt, updatedAt);
    }

    public UserAchievement userAchievement() {
        return new UserAchievement(++id, achievement(), userId, createdAt, updatedAt);
    }

    public AchievementFilterDto filters() {
        return AchievementFilterDto.builder()
                .title("filter")
                .description("filter")
                .rarity(Rarity.EPIC)
                .build();
    }

    public AchievementDto achievementDto() {
        return AchievementDto.builder()
                .id(++id)
                .title(title)
                .description(description)
                .rarity(rarity)
                .points(points)
                .build();
    }

    public AchievementProgressDto achievementProgresDto() {
        return AchievementProgressDto.builder()
                .id(achievementId)
                .achievement(achievementDto())
                .userId(userId)
                .currentPoints(points)
                .version(version)
                .build();
    }

    private List<UserAchievement> createUserAchievements() {
        UserAchievement userAchievement = UserAchievement.builder()
                .id(++id)
                .achievement(achievement())
                .build();

        return List.of(userAchievement);
    }

    private List<AchievementProgress> createProgresses() {
        AchievementProgress userAchievement = AchievementProgress.builder()
                .id(++id)
                .achievement(achievement())
                .build();

        return List.of(userAchievement);
    }
}
