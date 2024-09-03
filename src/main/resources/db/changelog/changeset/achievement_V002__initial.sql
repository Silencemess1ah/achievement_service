CREATE TABLE user_achievement_event_counter
(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    user_id        bigint NOT NULL,
    all_current_points bigint NOT NULL,
    event_type VARCHAR(128)  NOT NULL,
    version        bigint NOT NULL DEFAULT 0
);