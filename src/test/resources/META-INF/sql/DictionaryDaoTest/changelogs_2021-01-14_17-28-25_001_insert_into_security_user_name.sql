-- noinspection SqlWithoutWhere
DELETE FROM security.user_name;
INSERT INTO security.user_name
    (id, user_name, password,
     create_time, update_time, enabled, visible, flags)
  VALUES
    ('00000000-0000-0000-0000-000000000001', 'userName1', 'password1',
     '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);
