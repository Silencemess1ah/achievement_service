package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class AchievementMapper {

    public AchievementDto toDto(Achievement achievement);

    public Achievement toEntity(AchievementDto achievementDto);

    @Mapping(target = "receivedAt", expression = "java(LocalDateTime.now())")
    public AchievementReceivedEvent toEvent(long userId, AchievementDto achievement);
}
