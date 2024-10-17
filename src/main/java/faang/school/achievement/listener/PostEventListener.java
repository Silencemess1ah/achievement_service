package faang.school.achievement.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class PostEventListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
