-- noinspection SqlWithoutWhere
DELETE FROM db.link;
INSERT INTO db.link
(id, link, user_name, create_time, update_time, enabled, visible, flags)
VALUES
('00000000-0000-0000-0000-000000000002', 'link1', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);