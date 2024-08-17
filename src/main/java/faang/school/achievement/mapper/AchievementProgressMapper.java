package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.AchievementProgress;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementProgressMapper {
    AchievementProgress toEntity(AchievementProgressDto achievementProgressDto);

    AchievementProgressDto toDto(AchievementProgress achievementProgress);
}