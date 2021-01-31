DROP TABLE IF EXISTS dictionary.codifier;
CREATE TABLE dictionary.codifier (
  id            BIGINT  AUTO_INCREMENT  PRIMARY KEY,
  code          VARCHAR(64)
                CONSTRAINT UC_81b0_dictionary_code_must_be_different
                UNIQUE,
  value         VARCHAR(10485760),
  user_name     VARCHAR(64),
                CONSTRAINT FK_84d3_dictionary_codifier_security_user_name
                FOREIGN KEY (user_name)
                REFERENCES  security.user_name (user_name)
                ON DELETE CASCADE ON UPDATE CASCADE,
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled       BOOLEAN                                 DEFAULT false,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
