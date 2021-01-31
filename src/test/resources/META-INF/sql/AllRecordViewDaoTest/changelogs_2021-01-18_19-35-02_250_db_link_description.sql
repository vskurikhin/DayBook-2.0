CREATE TABLE db.link_description (
  id                UUID DEFAULT RANDOM_UUID() NOT NULL,
  news_links_id     UUID                         NOT NULL,
                    CONSTRAINT FK_8f05_db_link_description_need_news_links_id
                    FOREIGN KEY (news_links_id)
                    REFERENCES  db.news_links (id)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  link_id           UUID                         NOT NULL,
                    CONSTRAINT FK_4b64_db_link_description_need_link_id
                    FOREIGN KEY (link_id)
                    REFERENCES  db.link (id)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  description       VARCHAR(128)                 NOT NULL,
  details           VARCHAR(8192),
  user_name         VARCHAR(64),
                    CONSTRAINT FK_47f3_db_link_description_security_user_name
                    FOREIGN KEY (user_name)
                    REFERENCES  security.user_name (user_name)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  create_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled           BOOLEAN                                 DEFAULT true,
  visible           BOOLEAN                                 DEFAULT true,
  flags             INT,
                    CONSTRAINT UC_9745_db_link_description_must_be_different
                    UNIQUE (news_links_id, link_id)
);
