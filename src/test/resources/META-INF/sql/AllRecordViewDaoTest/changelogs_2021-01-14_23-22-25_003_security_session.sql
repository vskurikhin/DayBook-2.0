DROP TABLE IF EXISTS security.session;
CREATE TABLE security.session (
  user_name     VARCHAR(64) PRIMARY KEY  NOT NULL,
                CONSTRAINT FK_7854_security_session_user_name
                FOREIGN KEY (user_name)
                REFERENCES  security.user_name (user_name)
                ON DELETE CASCADE ON UPDATE CASCADE,
  session_id    UUID                         NOT NULL,
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  end_time      TIMESTAMP WITHOUT TIME ZONE  NOT NULL,
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled       BOOLEAN                                 DEFAULT true,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
