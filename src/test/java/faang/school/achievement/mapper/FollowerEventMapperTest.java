package faang.school.achievement.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FollowerEventMapperTest {

    private FollowerEventMapper followerEventMapper;

    @BeforeEach
    void setUp() {
        followerEventMapper = Mappers.getMapper(FollowerEventMapper.class);
    }

    @Test
    void toLocalDateTime_shouldConvertTimestampToLocalDateTime() {
        long seconds = Instant.now().getEpochSecond();
        int nanos = 123456789;
        Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(seconds)
                .setNanos(nanos)
                .build();
        LocalDateTime expected = LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds, nanos), ZoneId.systemDefault());

        LocalDateTime result = followerEventMapper.toLocalDateTime(timestamp);

        assertEquals(expected, result);
    }

    @Test
    void toLocalDateTime_shouldReturnNullWhenTimestampIsNull() {
        LocalDateTime result = followerEventMapper.toLocalDateTime(null);

        assertNull(result);
    }
}
