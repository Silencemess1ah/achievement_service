package faang.school.achievement.handler;

import faang.school.achievement.event.MentorshipStartEvent;

public interface EventHandler {

    void handleEvent(MentorshipStartEvent mentorshipStartEvent);
}
