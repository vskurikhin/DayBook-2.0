CREATE TABLE db.map  (
  id            UUID DEFAULT RANDOM_UUID() NOT NULL,
  key           VARCHAR(1024),
  value         JSON,
  value_type_id BIGINT,
                CONSTRAINT FK_4444_db_map_value_type_id
                FOREIGN KEY (value_type_id)
                REFERENCES  dictionary.value_type (id)
                ON DELETE CASCADE ON UPDATE CASCADE,
  user_name     VARCHAR(64),
                CONSTRAINT FK_4455_db_map_security_user_name
                FOREIGN KEY (user_name)
                REFERENCES  security.user_name (user_name)
                ON DELETE CASCADE ON UPDATE CASCADE,
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled       BOOLEAN                                 DEFAULT true,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
