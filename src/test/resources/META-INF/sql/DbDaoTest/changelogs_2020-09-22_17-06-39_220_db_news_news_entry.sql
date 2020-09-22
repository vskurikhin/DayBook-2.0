CREATE TABLE db.news_entry (
  news_entry_id     UUID DEFAULT RANDOM_UUID() NOT NULL,
  news_group_id     UUID,
                    CONSTRAINT FK_db_news_entry_need_news_group_2134
                    FOREIGN KEY (news_group_id)
                    REFERENCES  db.news_group (news_group_id)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  title             VARCHAR(128)                 NOT NULL,
  content           VARCHAR(10485760),
  user_name         VARCHAR(64),
                    CONSTRAINT FK_4ce1_db_news_entry_security_user_name_user_name
                    FOREIGN KEY (user_name)
                    REFERENCES  security.user_name (user_name)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  create_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled           BOOLEAN                                 DEFAULT true,
  visible           BOOLEAN                                 DEFAULT true,
  flags             INT
);
