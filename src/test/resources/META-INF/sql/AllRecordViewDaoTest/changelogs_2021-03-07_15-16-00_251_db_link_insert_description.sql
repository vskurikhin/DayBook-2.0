-- noinspection SqlWithoutWhere
DELETE FROM db.link_description;
INSERT INTO db.link_description
(id, news_links_id, link_id, description, details, user_name, create_time, update_time, enabled, visible, flags)
VALUES
('00000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000002', 'description1', 'details1', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);
