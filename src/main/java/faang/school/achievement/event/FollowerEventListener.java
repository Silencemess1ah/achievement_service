package faang.school.achievement.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.FollowerEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FollowerEventListener extends BaseListener<FollowerEvent> {

    @Autowired
    public FollowerEventListener(FollowerEventHandler followerEventHandler, ObjectMapper objectMapper) {
        super(followerEventHandler, objectMapper, FollowerEvent.class);
    }
}
