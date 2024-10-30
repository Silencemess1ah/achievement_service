package faang.school.achievement.handler;

import faang.school.achievement.dto.AlbumCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LibrarianAchievementHandlerTest {

    @InjectMocks
    private LibrarianAchievementHandler librarianAchievementHandler;

    @Test
    void shouldReturnCorrectAchievementName() {
        String achievementName = librarianAchievementHandler.getAchievementName();
        assertEquals("LIBRARIAN", achievementName);
    }

    @Test
    void shouldExtractUserIdFromEvent() {
        AlbumCreatedEvent event = new AlbumCreatedEvent(LocalDateTime.now(), 1L, 2L, "name");
        event.setUserId(123L);

        long userId = librarianAchievementHandler.getUserIdFromEvent(event);

        assertEquals(123L, userId);
    }

    @Test
    void shouldReturnCorrectEventClass() {
        assertEquals(AlbumCreatedEvent.class, librarianAchievementHandler.getEventClass());
    }
}
