package faang.school.achievement.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AchievementEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AchievementEventPublisher {
    private final KafkaTemplate<byte[], byte[]> kafkaTemplate;
    @Value("${spring.kafka.topics.achievement-received.name}")
    private String topic;
    private final ObjectMapper mapper;

    public void sendMessage(AchievementEvent message) {
        log.info("try to send {} to topic named {}", message, topic);
        try {
            kafkaTemplate.send(topic, mapper.writeValueAsBytes(message));
        } catch (JsonProcessingException e) {
            log.error("cannot write {} as byte array", message, e);
        }
    }
}
