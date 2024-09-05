package faang.school.achievement.eventhandler.post;

import faang.school.achievement.event.post.PostEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OpinionLeaderAchievementHandler extends PostEventHandler{
    @Override
    @Async
    public void handle(PostEvent event) {

    }

    @Override
    @Async
    public boolean canBeHandled(PostEvent event) {
        return false;
    }
}
