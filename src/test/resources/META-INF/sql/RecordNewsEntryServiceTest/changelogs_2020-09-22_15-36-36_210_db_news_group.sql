CREATE TABLE db.news_group (
  news_group_id     UUID DEFAULT RANDOM_UUID() NOT NULL,
  group_name        VARCHAR(64)                  NOT NULL
                    CONSTRAINT UC_db_news_group_must_be_different_4ce1
                    UNIQUE,
  user_name         VARCHAR(64),
                    CONSTRAINT FK_47f3_db_news_group_security_user_name_user_name
                    FOREIGN KEY (user_name)
                    REFERENCES  security.user_name (user_name)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  create_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled           BOOLEAN                                 DEFAULT true,
  visible           BOOLEAN                                 DEFAULT true,
  flags             INT
);