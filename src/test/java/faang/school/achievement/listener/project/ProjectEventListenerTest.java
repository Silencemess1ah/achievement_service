package faang.school.achievement.listener.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.project.ProjectEvent;
import faang.school.achievement.messaging.handler.EventHandler;
import faang.school.achievement.messaging.listener.project.ProjectEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectEventListenerTest {

    @InjectMocks
    private ProjectEventListener projectEventListener;

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private EventHandler<ProjectEvent> projectEventHandler;
    @Spy
    private List<EventHandler<ProjectEvent>> projectEventHandlers;

    private ProjectEvent projectEvent;
    private String data = "Data";

    @BeforeEach
    void setUp() {
        projectEvent = ProjectEvent.builder()
                .eventId(UUID.randomUUID())
                .projectId(1)
                .authorId(1)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @Test
    void testOnMessageShouldThrow() throws IOException {
        when(objectMapper.readValue(data.getBytes(), ProjectEvent.class)).thenThrow(IOException.class);

        assertThrows(RuntimeException.class, () -> projectEventListener.onMessage(data));
    }

    @Test
    void testOnMessageSuccess() throws IOException {
        when(objectMapper.readValue(data.getBytes(), ProjectEvent.class)).thenReturn(projectEvent);
        when(projectEventHandlers.iterator()).thenReturn(List.of(projectEventHandler).iterator());
        doNothing().when(projectEventHandler).handle(projectEvent);

        projectEventListener.onMessage(data);

        verify(objectMapper, times(1)).readValue(data.getBytes(), ProjectEvent.class);
        verify(projectEventHandler).handle(projectEvent);
    }
}