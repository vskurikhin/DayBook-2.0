ALTER TABLE db.record ADD public_time TIMESTAMP WITHOUT TIME ZONE DEFAULT now();
UPDATE db.record SET public_time = update_time;
ALTER TABLE db.record ALTER public_time SET NOT NULL;
