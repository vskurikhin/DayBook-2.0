CREATE TABLE db.tagget (
  id                UUID DEFAULT RANDOM_UUID() NOT NULL,
  record_id         UUID                         NOT NULL,
                    CONSTRAINT FK_48f8_db_tagget_record_id
                    FOREIGN KEY (record_id)
                    REFERENCES  db.record (id)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  tag_label_id      CHAR(16)                     NOT NULL,
                    CONSTRAINT FK_4b64_db_tagget_tag_label_id
                    FOREIGN KEY (tag_label_id)
                    REFERENCES  dictionary.tag_label (id)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  user_name         VARCHAR(64),
                    CONSTRAINT FK_4338_db_link_description_security_user_name
                    FOREIGN KEY (user_name)
                    REFERENCES  security.user_name (user_name)
                    ON DELETE CASCADE ON UPDATE CASCADE,
  create_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time       TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled           BOOLEAN                                 DEFAULT true,
  visible           BOOLEAN                                 DEFAULT true,
  flags             INT
);
