-- noinspection SqlWithoutWhere
DELETE FROM dictionary.word;
INSERT INTO dictionary.word
    (id, word, user_name, create_time, update_time, enabled, visible, flags)
  VALUES
    (1, 'word1', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);