DROP TABLE IF EXISTS security.user_name;
CREATE TABLE security.user_name (
  id            UUID DEFAULT RANDOM_UUID() NOT NULL,
  user_name     VARCHAR(64)        NOT NULL
                CONSTRAINT UC_3109_security_user_name_must_be_unique
                UNIQUE,
  password      VARCHAR(1024)      NOT NULL,
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled       BOOLEAN                                 DEFAULT true,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
