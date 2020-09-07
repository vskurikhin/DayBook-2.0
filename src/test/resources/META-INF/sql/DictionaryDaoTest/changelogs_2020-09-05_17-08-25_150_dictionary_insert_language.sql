-- noinspection SqlWithoutWhere
DELETE FROM dictionary.language;
INSERT INTO dictionary.language
    (language_id, language, user_name, create_time, update_time, enabled, visible, flags)
VALUES
    (1, 'language1', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);
