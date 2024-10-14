package faang.school.achievement.service;

import faang.school.achievement.dto.CommentEvent;
import org.springframework.stereotype.Service;

@Service
public abstract class CommentEventHandler implements EventHandler {

    @Override
    public void handleEvent(CommentEvent event) {

    }
}
