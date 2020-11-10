CREATE TABLE db.news_entry (
  news_entry_id     UUID DEFAULT RANDOM_UUID() NOT NULL,
                    CONSTRAINT FK_db_news_entry_need_record_4da8
                    FOREIGN KEY (news_entry_id)
                    REFERENCES  db.record (record_id)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  news_group_id     UUID,
                    CONSTRAINT FK_db_news_entry_need_news_group_42ee
                    FOREIGN KEY (news_group_id)
                    REFERENCES  db.news_group (news_group_id)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  user_name         VARCHAR(64),
                    CONSTRAINT FK_47f3_db_news_entry_security_user_name_user_name
                    FOREIGN KEY (user_name)
                    REFERENCES  security.user_name (user_name)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  title             VARCHAR(256)                 NOT NULL,
  content           VARCHAR(10485760),
  create_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled           BOOLEAN                                 DEFAULT true,
  visible           BOOLEAN                                 DEFAULT true,
  flags             INT
);
