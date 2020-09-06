-- noinspection SqlWithoutWhere
DELETE FROM dictionary.codifier;
INSERT INTO dictionary.codifier
  (codifier_id, code, value, user_name, create_time, update_time, is_disabled, visible, flags)
VALUES
  (1, 'code1', 'value1', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', false, true, 0);