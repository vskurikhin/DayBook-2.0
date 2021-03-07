-- noinspection SqlWithoutWhere
DELETE FROM db.record;
INSERT INTO db.record
(id, position, type, user_name, create_time, public_time, update_time, enabled, visible, flags)
VALUES
('00000000-0000-0000-0000-000000000001', 1, 'NewsEntry', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0),
('00000000-0000-0000-0000-000000000002', 2, 'NewsLinks', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0),
('00000000-0000-0000-0000-000000000003', 13, 'Article', 'userName1', '1970-01-01 00:00:00', '1970-01-01 13:00:00', '1970-01-01 13:00:00', true, true, 0);

INSERT INTO db.record
    (position, type, user_name, enabled)
  VALUES
    (1, 'type1', 'userName1', false);
