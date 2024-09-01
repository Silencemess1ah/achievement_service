package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.AchievementEvent;
import faang.school.achievement.event.LikeEvent;
import faang.school.achievement.handler.AllLoveAchievementHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LikeEventListener implements MessageListener {
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final AllLoveAchievementHandler allLoveAchievementHandler;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String body = new String(message.getBody());

        LikeEvent likeEvent;
        try {
            likeEvent = objectMapper.readValue(body, LikeEvent.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        allLoveAchievementHandler.handler(likeEvent);
    }
}
