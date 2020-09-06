DROP TABLE IF EXISTS dictionary.vocabulary;
CREATE TABLE dictionary.vocabulary (
  vocabulary_id BIGINT  AUTO_INCREMENT  PRIMARY KEY  NOT NULL,
  word_id       BIGINT  NOT NULL,
                CONSTRAINT FK_db_news_tag_need_dictionary_tag_80e4
                FOREIGN KEY (word_id)
                REFERENCES  dictionary.word (word_id)
                ON DELETE CASCADE ON UPDATE CASCADE,
  value         VARCHAR(2147483647),
  user_name     VARCHAR(256),
  create_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  update_time   TIMESTAMP WITHOUT TIME ZONE  NOT NULL   DEFAULT now(),
  is_disabled   BOOLEAN                                 DEFAULT false,
  visible       BOOLEAN                                 DEFAULT true,
  flags         INT
);
