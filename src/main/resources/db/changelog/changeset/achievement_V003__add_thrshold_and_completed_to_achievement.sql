ALTER TABLE achievement
ADD COLUMN threshold bigint NOT NULL DEFAULT 0;

UPDATE achievement
SET threshold = CASE
    WHEN title = 'COLLECTOR' THEN 100
    WHEN title = 'MR PRODUCTIVITY' THEN 1000
    WHEN title = 'EXPERT' THEN 1000
    WHEN title = 'SENSEI' THEN 30
    WHEN title = 'MANAGER' THEN 10
    WHEN title = 'CELEBRITY' THEN 1000000
    WHEN title = 'ROOKY_WRITER' THEN 1
    WHEN title = 'BEGINNER_WRITER' THEN 10
    WHEN title = 'MIDDLE_WRITER' THEN 50
    WHEN title = 'SENIOR_WRITER' THEN 100
    WHEN title = 'LEV_TOLSTOY' THEN 500
    WHEN title = 'HANDSOME' THEN 1
    ELSE 0
END;

ALTER TABLE achievement
ADD COLUMN base_value bigint NOT NULL DEFAULT 0;

UPDATE achievement
SET base_value = CASE
    WHEN title = 'ROOKY_WRITER' THEN 0
    WHEN title = 'BEGINNER_WRITER' THEN 1
    WHEN title = 'MIDDLE_WRITER' THEN 11
    WHEN title = 'SENIOR_WRITER' THEN 66
    WHEN title = 'LEV_TOLSTOY' THEN 166
    ELSE 0
END;

ALTER TABLE user_achievement_progress
ADD COLUMN completed boolean NOT NULL DEFAULT false;