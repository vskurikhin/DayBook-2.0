DROP TABLE IF EXISTS security.role;
CREATE TABLE security.role (
  role_id       UUID DEFAULT RANDOM_UUID() NOT NULL,
  role_name     VARCHAR(32)        NOT NULL,
                CONSTRAINT UC_9c54_security_id_role_name_must_be_unique
                UNIQUE (role_id, role_name),
  user_name     VARCHAR(64),
                CONSTRAINT FK_7854_security_role_security_user_name_user_name
                FOREIGN KEY (user_name)
                REFERENCES  security.user_name (user_name)
                ON DELETE CASCADE ON UPDATE CASCADE,
                CONSTRAINT UC_41a3_role_name_user_name_must_be_unique
                UNIQUE (role_name, user_name),
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled       BOOLEAN                                 DEFAULT true,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);