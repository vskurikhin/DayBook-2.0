DROP TABLE IF EXISTS dictionary.language;
CREATE TABLE dictionary.language (
  id            BIGINT  AUTO_INCREMENT  PRIMARY KEY,
  language      VARCHAR(256),
  user_name     VARCHAR(64),
                CONSTRAINT FK_4792_dictionary_language_security_user_name
                FOREIGN KEY (user_name)
                REFERENCES  security.user_name (user_name)
                ON DELETE CASCADE ON UPDATE CASCADE,
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled       BOOLEAN                                 DEFAULT true,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
