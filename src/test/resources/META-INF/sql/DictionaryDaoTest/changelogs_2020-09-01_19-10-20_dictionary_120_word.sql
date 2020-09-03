CREATE TABLE dictionary.word (
  word_id       BIGINT  AUTO_INCREMENT  PRIMARY KEY  NOT NULL,
  word          VARCHAR(256)  NOT NULL
                CONSTRAINT UC_dictionary_word_must_be_different_63ce
                UNIQUE,
  user_name     VARCHAR(256),
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  is_disabled   BOOLEAN                                 DEFAULT false,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
