CREATE OR REPLACE VIEW db.tagget_record_view AS SELECT
    re.id record_id,
    re.position record_position,
    re.type record_type,
    re.user_name record_user_name,
    re.create_time record_create_time,
    re.update_time record_update_time,
    re.enabled record_enabled,
    re.visible record_visible,
    re.flags record_flags,
    (   SELECT array_agg(tl.label)
          FROM db.tagget ta
          LEFT JOIN dictionary.tag_label tl
            ON tl.id = ta.tag_label_id AND tl.enabled AND ta.enabled
         WHERE re.id = ta.record_id AND ta.enabled
    ) tags
  FROM db.record re
 GROUP BY
    re.id,
    re.position,
    re.type,
    re.user_name,
    re.create_time,
    re.update_time,
    re.enabled,
    re.visible,
    re.flags
;
