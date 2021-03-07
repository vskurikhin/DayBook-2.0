CREATE OR REPLACE VIEW db.tagget_record_view AS SELECT
    re.id          id,
    re.position    position,
    re.type        type,
    re.user_name   user_name,
    re.public_time public_time,
    re.visible     visible,
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
    re.public_time,
    re.visible
HAVING re.enabled
;
