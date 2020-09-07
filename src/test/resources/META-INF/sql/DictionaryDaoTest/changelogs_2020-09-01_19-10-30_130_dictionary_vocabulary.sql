DROP TABLE IF EXISTS dictionary.vocabulary;
CREATE TABLE dictionary.vocabulary (
  vocabulary_id BIGINT  AUTO_INCREMENT  PRIMARY KEY  NOT NULL,
  word_id       BIGINT  NOT NULL,
                CONSTRAINT FK_80e4_db_news_tag_need_dictionary_tag
                FOREIGN KEY (word_id)
                REFERENCES  dictionary.word (word_id)
                ON DELETE CASCADE ON UPDATE CASCADE,
  value         VARCHAR(10485760),
  user_name     VARCHAR(64),
                CONSTRAINT FK_92c6_dictionary_vocabulary_security_user_name_user_name
                FOREIGN KEY (user_name)
                REFERENCES  security.user_name (user_name)
                ON DELETE CASCADE ON UPDATE CASCADE,
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  enabled       BOOLEAN                                 DEFAULT true,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);