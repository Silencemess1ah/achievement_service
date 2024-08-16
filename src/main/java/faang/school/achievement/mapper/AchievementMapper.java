package faang.school.achievement.mapper;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {
    AchievementDto toDto(Achievement achievement);

    @Mapping(source = "achievement.id", target = "achievementId")
    @Mapping(source = "achievement.title", target = "achievementTitle")
    @Mapping(source = "achievement.description", target = "achievementDescription")
    @Mapping(source = "achievement.rarity", target = "achievementRarity")
    UserAchievementDto toDto(UserAchievement userAchievement);

    @Mapping(source = "achievement.id", target = "achievementId")
    @Mapping(source = "achievement.title", target = "achievementTitle")
    @Mapping(source = "achievement.description", target = "achievementDescription")
    @Mapping(source = "achievement.rarity", target = "achievementRarity")
    AchievementProgressDto toDto(AchievementProgress achievementProgress);

    Achievement toEntity(AchievementDto dto);

    UserAchievement toEntity(UserAchievementDto dto);

    AchievementProgress toEntity(AchievementProgressDto dto);
}
