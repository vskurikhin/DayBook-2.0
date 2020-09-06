DROP TABLE IF EXISTS dictionary.language;
CREATE TABLE dictionary.language (
  language_id   BIGINT  AUTO_INCREMENT  PRIMARY KEY  NOT NULL,
  language      VARCHAR(256),
  user_name     VARCHAR(256),
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  is_disabled   BOOLEAN                                 DEFAULT false,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
