-- noinspection SqlWithoutWhere
DELETE FROM dictionary.tag_label;
INSERT INTO dictionary.tag_label
    (tag_label_id, label, user_name, create_time, update_time, enabled, visible, flags)
VALUES
    ('1', 'label1', 'userName1', '1970-01-01 00:00:00', '1970-01-01 00:00:00', true, true, 0);
