package faang.school.achievement.mapper;

import com.google.protobuf.Timestamp;
import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.protobuf.generate.FollowerEventProto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface FollowerEventMapper {

    @Mapping(target = "eventTime", expression = "java(toLocalDateTime(protoEvent.getEventTime()))")
    FollowerEvent toFollowerEvent(FollowerEventProto.FollowerEvent protoEvent);

    default LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return Optional.ofNullable(timestamp).map(ts -> {
            Instant instant = Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos());
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }).orElse(null);
    }
}
