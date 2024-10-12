package faang.school.achievement.listener;

import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.List;

public class PostEventListener implements MessageListener {
    private List<EventHandler> handlers;
    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
