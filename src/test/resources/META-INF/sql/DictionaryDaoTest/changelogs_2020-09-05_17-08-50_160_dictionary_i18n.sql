DROP TABLE IF EXISTS dictionary.i18n;
CREATE TABLE dictionary.i18n (
  i18n_id       BIGINT  AUTO_INCREMENT  PRIMARY KEY  NOT NULL,
  language_id   BIGINT  NOT NULL,
                CONSTRAINT FK_dictionary_language_language_id_7344
                FOREIGN KEY (language_id)
                REFERENCES  dictionary.language (language_id)
                ON DELETE CASCADE ON UPDATE CASCADE,
  message       VARCHAR(10485760),
  translation   VARCHAR(10485760),
  user_name     VARCHAR(64),
                CONSTRAINT FK_4b19_dictionary_i18n_security_user_name_user_name
                FOREIGN KEY (user_name)
                REFERENCES  security.user_name (user_name)
                ON DELETE CASCADE ON UPDATE CASCADE,
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled       BOOLEAN                                 DEFAULT true,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);