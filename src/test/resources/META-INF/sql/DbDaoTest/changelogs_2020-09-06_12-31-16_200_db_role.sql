DROP TABLE IF EXISTS db.role;
CREATE TABLE db.role (
  role_id       UUID DEFAULT RANDOM_UUID() NOT NULL PRIMARY KEY,
  role_name     VARCHAR(32)        NOT NULL
                CONSTRAINT UC_db_role_name_must_be_unique_31e0
                UNIQUE,
                CONSTRAINT UC_db_id_role_name_must_be_unique_77db
                UNIQUE (role_id, role_name),
  user_name     VARCHAR(256),
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  is_disabled   BOOLEAN                                 DEFAULT false,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
