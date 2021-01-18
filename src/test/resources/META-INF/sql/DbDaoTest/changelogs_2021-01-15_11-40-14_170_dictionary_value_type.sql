CREATE SEQUENCE dictionary.value_type_seq START 1;

CREATE TABLE dictionary.value_type (
  id            BIGINT  AUTO_INCREMENT  PRIMARY KEY  NOT NULL,
  value_type    VARCHAR(1024),
  user_name     VARCHAR(64),
                CONSTRAINT FK_5af5_dictionary_value_type_security_user_name
                FOREIGN KEY (user_name)
                REFERENCES  security.user_name (user_name)
                ON DELETE CASCADE ON UPDATE CASCADE,
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled       BOOLEAN                                 DEFAULT true,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
