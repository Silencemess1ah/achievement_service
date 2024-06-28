package faang.school.achievement.mapper;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.event.AchievementReceivedEvent;
import faang.school.achievement.model.Achievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {LocalDateTime.class})
public interface AchievementMapper {

    AchievementDto toDto(Achievement achievement);

    Achievement toEntity(AchievementDto achievementDto);

    @Mapping(target = "receivedAt", expression = "java(LocalDateTime.now())")
    AchievementReceivedEvent toEvent(long userId, AchievementDto achievement);
}
