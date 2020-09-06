-- noinspection SqlWithoutWhere
DELETE FROM dictionary.word;
INSERT INTO dictionary.word
    (word_id, word, user_name, create_time, update_time, is_disabled, visible, flags)
    VALUES
    (1, 'word1', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', false, true, 0);