-- noinspection SqlWithoutWhere
DELETE FROM dictionary.i18n;
INSERT INTO dictionary.i18n
    (i18n_id, language_id, message, translation, user_name, create_time, update_time, enabled, visible, flags)
VALUES
    (1, 1, 'message1', 'translation1', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);
