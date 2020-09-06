-- noinspection SqlWithoutWhere
DELETE FROM db.role;
INSERT INTO db.role
    (role_id, role_name, user_name, create_time, update_time, is_disabled, visible, flags)
VALUES
    (1, 'roleName1', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', false, true, 0);