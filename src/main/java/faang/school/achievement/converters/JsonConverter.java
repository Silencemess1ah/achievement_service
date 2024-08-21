package faang.school.achievement.converters;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JsonConverter <T> {
    private final ObjectMapper objectMapper;

    public String toJson(T event){
        String json = null;
        try{
            json = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.warn("Issue occurred while converting event {}", event, e);
        }
        return json;
    }

    public T fromJson(String json, Class<T> eventClass){
        T event = null;
        try{
        event = objectMapper.readValue(json,eventClass);
    } catch (JsonProcessingException e) {
            log.warn("Issue occurred while converting string {} to event", json, e);
        }
        return event;
    }
}
