-- noinspection SqlWithoutWhere
DELETE FROM db.tagget;
INSERT INTO db.tagget
(id, record_id, tag_label_id, user_name, create_time, update_time, enabled, visible, flags)
VALUES
('1', '00000000-0000-0000-0000-000000000001', '1', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);
