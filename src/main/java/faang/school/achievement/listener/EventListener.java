package faang.school.achievement.listener;

import com.google.protobuf.InvalidProtocolBufferException;

public interface EventListener<T> {
    void onMessage(T event) throws InvalidProtocolBufferException;
}
