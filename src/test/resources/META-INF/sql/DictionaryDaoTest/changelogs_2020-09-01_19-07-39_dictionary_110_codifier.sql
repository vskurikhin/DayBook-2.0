DROP TABLE IF EXISTS dictionary.codifier;
CREATE TABLE dictionary.codifier (
  codifier_id   BIGINT  AUTO_INCREMENT  PRIMARY KEY  NOT NULL,
  code          VARCHAR(64)
                CONSTRAINT UC_dictionary_code_must_be_different_81b0
                UNIQUE,
  value         VARCHAR(10485760),
  user_name     VARCHAR(256),
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  is_disabled   BOOLEAN                                 DEFAULT false,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
