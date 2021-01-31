CREATE TABLE db.news_entry (
  id                UUID DEFAULT RANDOM_UUID() NOT NULL,
                    CONSTRAINT FK_9a77_db_news_entry_need_record
                    FOREIGN KEY (id)
                    REFERENCES  db.record (id)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  news_group_id     UUID,
                    FOREIGN KEY (news_group_id)
                    REFERENCES  db.news_group (id)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  title             VARCHAR(256)                 NOT NULL,
  content           VARCHAR(10485760),
  user_name         VARCHAR(64),
                    CONSTRAINT FK_47f3_db_news_entry_security_user_name
                    FOREIGN KEY (user_name)
                    REFERENCES  security.user_name (user_name)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  create_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled           BOOLEAN                                 DEFAULT true,
  visible           BOOLEAN                                 DEFAULT true,
  flags             INT
);
