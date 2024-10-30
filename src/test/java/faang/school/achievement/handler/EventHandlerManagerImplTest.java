package faang.school.achievement.handler;

import faang.school.achievement.dto.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventHandlerManagerImplTest {

    @Mock
    private EventHandler<TestEvent> firstMockEventHandler;

    @Mock
    private EventHandler<TestEvent> secondMockEventHandler;

    @Mock
    private ExecutorService executorService;

    @InjectMocks
    private EventHandlerManagerImpl<TestEvent> eventHandlerManager;

    private final TestEvent event = new TestEvent(LocalDateTime.now());

    @BeforeEach
    void setUp() {
        List<EventHandler<TestEvent>> eventHandlers = List.of(firstMockEventHandler, secondMockEventHandler);
        ReflectionTestUtils.setField(eventHandlerManager, "eventHandlers", eventHandlers);
    }

    @Test
    void testInitHandlers_addsHandlersToMap() {
        var correctResult = Map.of(TestEvent.class, List.of(firstMockEventHandler, secondMockEventHandler));
        when(firstMockEventHandler.getEventClass()).thenReturn(TestEvent.class);
        when(secondMockEventHandler.getEventClass()).thenReturn(TestEvent.class);

        eventHandlerManager.initHandlers();

        var result = eventHandlerManager.getMapEventByHandler();
        assertTrue(result.containsKey(TestEvent.class));
        assertEquals(correctResult, result);
    }


    @Test
    void testProcessEvent_callsHandlers() {
        var mapEventByHandler = Map.of(TestEvent.class, List.of(firstMockEventHandler, secondMockEventHandler));
        ReflectionTestUtils.setField(eventHandlerManager, "mapEventByHandler", mapEventByHandler);

        eventHandlerManager.processEvent(event);

        verify(executorService, times(2)).execute(any(Runnable.class));
    }

    @Test
    void testProcessEvent_noHandlersForEvent() {
        eventHandlerManager.processEvent(event);

        verify(firstMockEventHandler, never()).handleEventIfNotProcessed(any(TestEvent.class));
        verify(secondMockEventHandler, never()).handleEventIfNotProcessed(any(TestEvent.class));
        verify(executorService, never()).execute(any(Runnable.class));
    }

    private static class TestEvent extends Event {
        public TestEvent(LocalDateTime eventTime) {
            super(eventTime);
        }
    }
}
