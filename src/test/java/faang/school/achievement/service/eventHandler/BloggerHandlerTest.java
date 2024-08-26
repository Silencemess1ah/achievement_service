package faang.school.achievement.service.eventHandler;

import faang.school.achievement.dto.FollowerEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BloggerHandlerTest {
    @Spy
    private BloggerHandler bloggerHandler;
    private FollowerEvent event;
    private String achievementTitle;

    @BeforeEach
    void setUp() {
        event = new FollowerEvent();
        achievementTitle = "BLOGGER";
    }

    @Test
    void testHandle() {
        doNothing().when(bloggerHandler).process(any(), any());
        bloggerHandler.handle(event);
        verify(bloggerHandler, times(1)).process(event, achievementTitle);
    }
}