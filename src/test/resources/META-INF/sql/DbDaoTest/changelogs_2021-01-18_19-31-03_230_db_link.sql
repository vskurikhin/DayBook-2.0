CREATE TABLE db.link (
  id                UUID DEFAULT RANDOM_UUID() NOT NULL,
                    CONSTRAINT FK_4a13_db_link_need_record
                    FOREIGN KEY (id)
                    REFERENCES  db.record (id)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  link              VARCHAR(512)                 NOT NULL,
  user_name         VARCHAR(64),
                    CONSTRAINT FK_5551_db_link_security_user_name
                    FOREIGN KEY (user_name)
                    REFERENCES  security.user_name (user_name)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  create_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled           BOOLEAN                                 DEFAULT true,
  visible           BOOLEAN                                 DEFAULT true,
  flags             INT
);
