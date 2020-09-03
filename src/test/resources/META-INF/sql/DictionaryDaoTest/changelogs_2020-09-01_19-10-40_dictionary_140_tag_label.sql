SET MODE HSQLDB;

DROP TABLE IF EXISTS dictionary.tag_label;
DROP ALIAS IF EXISTS dictionary.next_val_tag_label_seq;
DROP SEQUENCE IF EXISTS dictionary.tag_label_seq;

CREATE SEQUENCE dictionary.tag_label_seq START 1;

CREATE ALIAS dictionary.next_val_tag_label_seq FOR "su.svn.daybook.domain.dao.db.Function.nextValueTagLabelSequence";

CREATE TABLE dictionary.tag_label (
  tag_label_id  CHAR(16) DEFAULT dictionary.next_val_tag_label_seq()  PRIMARY KEY  NOT NULL,
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
