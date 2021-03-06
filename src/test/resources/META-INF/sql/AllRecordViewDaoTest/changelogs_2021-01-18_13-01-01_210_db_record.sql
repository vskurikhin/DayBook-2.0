CREATE TABLE db.record (
  id                UUID DEFAULT RANDOM_UUID() NOT NULL,
  position          INTEGER                      NOT NULL  DEFAULT 1,
  type              VARCHAR(256)                 NOT NULL,
  user_name         VARCHAR(64),
                    CONSTRAINT FK_9103_db_record_security_user_name
                    FOREIGN KEY (user_name)
                    REFERENCES  security.user_name (user_name)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  create_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled           BOOLEAN                                 DEFAULT true,
  visible           BOOLEAN                                 DEFAULT true,
  flags             INT
);
