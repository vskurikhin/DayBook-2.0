CREATE OR REPLACE VIEW db.all_record_view AS SELECT
    re.id record_id,
    re.position record_position,
    re.type record_type,
    re.user_name record_user_name,
    re.create_time record_create_time,
    re.update_time record_update_time,
    re.enabled record_enabled,
    re.visible record_visible,
    re.flags record_flags,
    ( SELECT array_agg(tl.label)
        FROM db.tagget ta
        LEFT JOIN dictionary.tag_label tl
          ON tl.id = ta.tag_label_id AND tl.enabled AND ta.enabled
       WHERE re.id = ta.record_id AND ta.enabled
    ) tags,
    ar.news_group_id article_news_group_id,
    ar.title article_title,
    ar.include article_include,
    ar.anchor article_anchor,
    ar.summary article_summary,
    ar.user_name article_user_name,
    ar.flags article_flags,
    ne.news_group_id news_entry_news_group_id,
    ne.title news_entry_title,
    ne.content news_entry_content,
    ne.user_name news_entry_user_name,
    ne.flags news_entry_flags,
    nl.news_group_id news_links_news_group_id,
    nl.title news_links_title,
    nl.user_name news_links_user_name,
    nl.flags news_links_flags,
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
    re.user_name,
    re.create_time,
    re.update_time,
    re.enabled,
    re.visible,
    re.flags,
    ar.news_group_id,
    ar.title,
    ar.include,
    ar.anchor,
    ar.summary,
    ar.user_name,
    ar.flags,
    ne.news_group_id,
    ne.title,
    ne.content,
    ne.user_name,
    ne.flags,
    nl.news_group_id,
    nl.title,
    nl.user_name,
    nl.flags
;
