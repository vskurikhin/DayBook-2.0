CREATE SEQUENCE dictionary.setting_seq START 1;

CREATE TABLE dictionary.setting  (
  id            BIGINT  AUTO_INCREMENT  PRIMARY KEY  NOT NULL,
  key           VARCHAR(1024),
  value         VARCHAR(65536),
  value_type_id BIGINT,
                CONSTRAINT FK_5014_dictionary_setting_value_type_id
                FOREIGN KEY (value_type_id)
                REFERENCES  dictionary.value_type (id)
                ON DELETE CASCADE ON UPDATE CASCADE,
  user_name     VARCHAR(64),
                CONSTRAINT FK_9f10_dictionary_setting_security_user_name
                FOREIGN KEY (user_name)
                REFERENCES  security.user_name (user_name)
                ON DELETE CASCADE ON UPDATE CASCADE,
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled       BOOLEAN                                 DEFAULT true,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
