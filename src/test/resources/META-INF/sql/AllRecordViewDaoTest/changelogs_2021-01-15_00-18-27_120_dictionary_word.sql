DROP TABLE IF EXISTS dictionary.word;
CREATE TABLE dictionary.word (
  id            BIGINT  AUTO_INCREMENT  PRIMARY KEY,
  word          VARCHAR(256)  NOT NULL
                CONSTRAINT UC_63c5_dictionary_word_must_be_different
                UNIQUE,
  user_name     VARCHAR(64),
                CONSTRAINT FK_7854_dictionary_word_security_user_name
                FOREIGN KEY (user_name)
                REFERENCES  security.user_name (user_name)
                ON DELETE CASCADE ON UPDATE CASCADE,
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled       BOOLEAN                                 DEFAULT false,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
