package faang.school.achievement.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.converters.JsonConverter;
import faang.school.achievement.events.CommentEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        String json = "\"commentAuthorId\":1L, \"postId\":1L, \"commentId\":1L, \"content\":\"Test\"}";
        CommentEvent event = new CommentEvent(1L,1L,1L, "Test");
        when(objectMapper.writeValueAsString(any())).thenReturn(json);
        String actual = jsonConverter.toJson(event);
        assertEquals(json, actual);
    }

    @Test
    public void fromJson() throws Exception {
        String json = "\"commentAuthorId\":1L, \"postId\":1L, \"commentId\":1L, \"content\":\"Test\"}";
        CommentEvent event = new CommentEvent(1L,1L,1L, "Test");
        when(objectMapper.readValue(json, CommentEvent.class)).thenReturn(event);
        CommentEvent actual = jsonConverter.fromJson(json, CommentEvent.class);
        assertEquals(event, actual);
    }
}
