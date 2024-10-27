package faang.school.achievement.test_data;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.AchievementTitle;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;

public class TestDataAchievement {
    public Achievement getAchievement() {
        return Achievement.builder()
                .id(1L)
                .title(AchievementTitle.EVIL_COMMENTER)
                .description("testAchievement")
                .rarity(Rarity.COMMON)
                .points(2)
                .build();
    }

    public UserAchievement getUserAchievement() {
        return UserAchievement.builder()
                .id(1L)
                .achievement(getAchievement())
                .userId(1L)
                .build();
    }

    public AchievementProgress getAchievementProgress() {
        return AchievementProgress.builder()
                .id(1L)
                .achievement(getAchievement())
                .userId(1L)
                .currentPoints(2L)
                .build();
    }

    public AchievementFilterDto getAchievementFilterDto() {
        return AchievementFilterDto.builder()
                .titlePattern("test")
                .descriptionPattern("test")
                .rarityPattern("test")
                .build();
    }

    public AchievementDto getAchievementDto(){
        return AchievementDto.builder()
                .id(1L)
                .title(AchievementTitle.EVIL_COMMENTER)
                .description("testAchievement")
                .rarity(Rarity.COMMON)
                .points(2L)
                .build();
    }
    public UserAchievementDto getUserAchievementDto(){
        return UserAchievementDto.builder()
                .id(1L)
                .achievement(getAchievementDto())
                .userId(1L)
                .build();
    }

    public AchievementProgressDto getAchievementProgressDto(){
        return AchievementProgressDto.builder()
                .id(1L)
                .achievement(getAchievementDto())
                .userId(1L)
                .currentPoints(2L)
                .build();
    }
}
