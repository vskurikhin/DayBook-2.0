-- noinspection SqlWithoutWhere
DELETE FROM dictionary.vocabulary;
INSERT INTO dictionary.vocabulary
    (id, word_id, value, user_name, create_time, update_time, enabled, visible, flags)
  VALUES
    (1, 1, 'value1', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);