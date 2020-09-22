-- noinspection SqlWithoutWhere
DELETE FROM db.news_entry;
INSERT INTO db.news_entry
    (news_entry_id, news_group_id, title, content, user_name, create_time, update_time, enabled, visible, flags)
  VALUES
    ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'title1', 'content1', 'userName1',
     '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);
