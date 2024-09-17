DROP INDEX user_achievement_progress_idx;
DROP TABLE user_achievement_progress;
DROP INDEX user_achievement_idx;
DROP TABLE user_achievement;
DROP TABLE achievement;

DELETE FROM databasechangelog WHERE filename = 'db/changelog/changeset/achievement_V001__initial.sql';
DELETE FROM databasechangelog WHERE filename = 'db/changelog/changeset/achievement_setup_V002.sql';
DELETE FROM databasechangelog WHERE filename = 'db/changelog/changeset/achievement_V003__insert_EvilCommenter.sql';
DELETE FROM databasechangelog WHERE filename = 'db/changelog/changeset/achievement_V004__insert_blogger.sql';
