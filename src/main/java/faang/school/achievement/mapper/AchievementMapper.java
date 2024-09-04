package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Evgenii Malkov
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {

    Achievement toEntity(AchievementDto achievementDto);

    AchievementDto toDto(Achievement achievement);

    List<AchievementDto> toListDto(List<Achievement> achievement);

}
