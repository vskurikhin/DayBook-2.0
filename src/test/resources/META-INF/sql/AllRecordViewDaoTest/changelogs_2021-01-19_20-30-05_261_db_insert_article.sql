-- noinspection SqlWithoutWhere
DELETE FROM db.article;
INSERT INTO db.article
(id, news_group_id, title, include, anchor, summary, user_name, create_time, update_time, enabled, visible, flags)
VALUES
('00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000001', 'title1', 'include1', 'anchor1', 'summary1', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);
