package faang.school.achievement.listener;

import com.google.protobuf.InvalidProtocolBufferException;
import faang.school.achievement.event.Event;
import faang.school.achievement.event.FollowerEvent;
import faang.school.achievement.event.handler.EventHandlerManager;
import faang.school.achievement.mapper.FollowerEventMapper;
import faang.school.achievement.protobuf.generate.FollowerEventProto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FollowerEventListener implements EventListener<byte[]> {

    private final FollowerEventMapper followerEventMapper;
    private final EventHandlerManager<Event> eventHandlerManager;

    @Override
    @KafkaListener(topics = "${spring.kafka.topics.follower.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(byte[] byteEvent) {
        log.info("Received follower event from follower topic");
        try {
            FollowerEventProto.FollowerEvent protoEvent = FollowerEventProto.FollowerEvent.parseFrom(byteEvent);
            FollowerEvent followerEvent = followerEventMapper.toFollowerEvent(protoEvent);
            eventHandlerManager.processEvent(followerEvent);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }
}
