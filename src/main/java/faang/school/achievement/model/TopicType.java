package faang.school.achievement.model;

public enum TopicType {

    ACHIEVEMENT_CHANNEL("ACHIEVEMENT_CHANNEL");

    private final String channelType;

    TopicType(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelType() {
        return channelType;
    }
}
