CREATE OR REPLACE VIEW db.all_record_view AS SELECT
    re.id,
    re.position,
    re.type,
    re.public_time,
    re.user_name   record_user_name,
    re.visible     record_visible,
    ( SELECT array_agg(tl.label)
        FROM db.tagget ta
        LEFT JOIN dictionary.tag_label tl
          ON tl.id = ta.tag_label_id AND tl.enabled AND ta.enabled
       WHERE re.id = ta.record_id AND ta.enabled
    ) tags,
    COALESCE(ar.news_group_id, ne.news_group_id, nl.news_group_id) news_group_id,
    COALESCE(ar.title, ne.title, nl.title)                         title,
    COALESCE(ar.user_name, ne.user_name, nl.user_name)             user_name,
    COALESCE(ar.visible, ne.visible, nl.visible)                   visible,
    ar.include     article_include,
    ar.anchor      article_anchor,
    ar.summary     article_summary,
    ne.content     news_entry_content,
    ( SELECT array_agg((li.link || ' ±± ' || ld.description || ' ±± ' || ld.details)::VARCHAR(10240))
        FROM db.link_description ld
        LEFT JOIN db.link li
        ON ld.link_id = li.id AND li.enabled
      WHERE ld.news_links_id = re.id AND ld.enabled
    ) links
  FROM db.record re
  LEFT JOIN db.article ar ON re.id = ar.id AND ar.enabled
  LEFT JOIN db.news_entry ne ON re.id = ne.id AND ne.enabled
  LEFT JOIN db.news_links nl ON re.id = nl.id AND nl.enabled
  GROUP BY
    re.id,
    re.position,
    re.type,
    re.public_time,
    re.user_name,
    re.visible,
    ar.news_group_id,
    ar.title,
    ar.include,
    ar.anchor,
    ar.summary,
    ar.user_name,
    ar.visible,
    ne.news_group_id,
    ne.title,
    ne.content,
    ne.user_name,
    ne.visible,
    nl.news_group_id,
    nl.title,
    nl.user_name,
    nl.visible
  HAVING re.enabled
;
