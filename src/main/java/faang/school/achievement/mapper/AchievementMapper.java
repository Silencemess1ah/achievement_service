package faang.school.achievement.mapper;

import faang.school.achievement.model.dto.AchievementDto;
import faang.school.achievement.model.entity.Achievement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {

    Achievement dtoToEntity(AchievementDto achievementDto);

    AchievementDto entityToDto(Achievement achievement);

}
