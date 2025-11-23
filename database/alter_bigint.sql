-- Исправить все ID поля на BIGINT (кроме width, height, position_x, position_y)
DO $$
BEGIN
    ALTER TABLE admin_audit_log ALTER COLUMN admin_id TYPE BIGINT;
    ALTER TABLE admin_audit_log ALTER COLUMN target_entity_id TYPE BIGINT;
    ALTER TABLE bookings ALTER COLUMN exhibition_stand_id TYPE BIGINT;
    ALTER TABLE bookings ALTER COLUMN artist_id TYPE BIGINT;
    ALTER TABLE exhibition_event ALTER COLUMN gallery_id TYPE BIGINT;
    ALTER TABLE exhibition_hall_map ALTER COLUMN exhibition_event_id TYPE BIGINT;
    ALTER TABLE exhibition_stand ALTER COLUMN exhibition_hall_map_id TYPE BIGINT;
    ALTER TABLE gallery_ownership ALTER COLUMN gallery_id TYPE BIGINT;
    ALTER TABLE gallery_ownership ALTER COLUMN owner_id TYPE BIGINT;
    ALTER TABLE user_activity_log ALTER COLUMN user_id TYPE BIGINT;

    RAISE NOTICE 'Все ID поля изменены на BIGINT';
END $$;