-- noinspection SqlWithoutWhere
DELETE FROM security.role;
INSERT INTO security.role
    (id, role_name, user_name, create_time, update_time, enabled, visible, flags)
  VALUES
    ('00000000-0000-0000-0000-000000000001', 'ROLE_USER', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);
