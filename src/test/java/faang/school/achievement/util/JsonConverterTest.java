package faang.school.achievement.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.converters.JsonConverter;
import faang.school.achievement.events.CommentEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class JsonConverterTest {
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private JsonConverter<CommentEvent> jsonConverter;

    @Test
    public void toJson() throws Exception {
        String json = "\"commentAuthorId\":1L, \"postId\":1L, \"commentId\":1L, \"sendAt\":\"15-04-2024'T'09:45:00\"}";
        CommentEvent event = new CommentEvent(1L,1L,1L, LocalDateTime.of(2024, Month.APRIL,15,9,45));
        when(objectMapper.writeValueAsString(any())).thenReturn(json);
        String actual = jsonConverter.toJson(event);
        assertEquals(json, actual);
    }

    @Test
    public void fromJson() throws Exception {
        String json = "\"commentAuthorId\":1L, \"postId\":1L, \"commentId\":1L, \"sendAt\":\"15-04-2024'T'09:45:00\"}";
        CommentEvent event = new CommentEvent(1L,1L,1L, LocalDateTime.of(2024, Month.APRIL,15,9,45));
        when(objectMapper.readValue(json, CommentEvent.class)).thenReturn(event);
        CommentEvent actual = jsonConverter.fromJson(json, CommentEvent.class);
        assertEquals(event, actual);
    }
}
