-- noinspection SqlWithoutWhere
DELETE FROM db.news_links;
INSERT INTO db.news_links
(id, news_group_id, user_name, title, create_time, update_time, enabled, visible, flags)
VALUES
('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'userName1', 'title1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);