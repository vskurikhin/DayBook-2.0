CREATE TABLE dictionary.tag_label (
  tag_label_id  CHAR(16)  PRIMARY KEY        NOT NULL,
  label         VARCHAR(128)                 NOT NULL
                CONSTRAINT UC_dictionary_tag_must_be_different_85b5
                UNIQUE,
  user_name     VARCHAR(256),
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  is_disabled   BOOLEAN                                 DEFAULT false,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
