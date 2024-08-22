package faang.school.achievement.messaging.listener;


import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.like.LikeEvent;
import faang.school.achievement.messaging.handler.like.LikeEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeEventListener implements MessageListener {
    private final List<LikeEventHandler> likeEventHandlers;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        LikeEvent likeEvent;

        try {
            likeEvent = objectMapper.readValue(message.getBody(), LikeEvent.class);
        } catch (IOException e) {
            String errorMessage = "Failed reading event: " + Arrays.toString(message.getBody());
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        likeEventHandlers.forEach(handler -> handler.handle(likeEvent));
    }
}
