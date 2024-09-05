package faang.school.achievement.eventhandler.post;

import faang.school.achievement.event.post.PostEvent;
import faang.school.achievement.eventhandler.EventHandler;

public abstract class PostEventHandler implements EventHandler<PostEvent> {
    @Override
    public void handle(PostEvent event) {

    }

    @Override
    public boolean canBeHandled(PostEvent event) {
        return EventHandler.super.canBeHandled(event);
    }
}
