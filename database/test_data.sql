\c art_gallery_platform;


-- Заполнение пользователей
INSERT INTO "users" (email, password_hash, full_name, role, phone_number, bio, avatar_url) VALUES
-- Администраторы
('admin@artplatform.com', 'hashed_password_1', 'Александр Иванов', 'ADMIN', '+79161234567', 'Главный администратор платформы', 'http://localhost:8080/api/files/avatars/admin1.jpg'),
('maria.admin@artplatform.com', 'hashed_password_2', 'Мария Петрова', 'ADMIN', '+79161234568', 'Администратор выставок', 'http://localhost:8080/api/files/avatars/admin2.jpg'),


-- Владельцы галерей
('gallery.owner@modernart.ru', 'hashed_password_3', 'Дмитрий Смирнов', 'GALLERY_OWNER', '+79161234569', 'Владелец галереи современного искусства', 'http://localhost:8080/api/files/avatars/owner1.jpg'),
('lena.gallery@classicart.com', 'hashed_password_4', 'Елена Козлова', 'GALLERY_OWNER', '+79161234570', 'Владелец классической галереи', 'http://localhost:8080/api/files/avatars/owner2.jpg'),
('art.space@mail.ru', 'hashed_password_5', 'Сергей Волков', 'GALLERY_OWNER', '+79161234571', 'Основатель арт-пространства', 'http://localhost:8080/api/files/avatars/owner3.jpg'),


-- Художники
('artist.ivanov@mail.ru', 'hashed_password_6', 'Петр Иванов', 'ARTIST', '+79161234572', 'Художник-абстракционист', 'http://localhost:8080/api/files/avatars/artist1.jpg'),
('anna.creative@gmail.com', 'hashed_password_7', 'Анна Сидорова', 'ARTIST', '+79161234573', 'Художник-портретист', 'http://localhost:8080/api/files/avatars/artist2.jpg'),
('mikhail.art@yandex.ru', 'hashed_password_8', 'Михаил Орлов', 'ARTIST', '+79161234574', 'Пейзажист', 'http://localhost:8080/api/files/avatars/artist3.jpg'),
('ekaterina.painter@mail.ru', 'hashed_password_9', 'Екатерина Новикова', 'ARTIST', '+79161234575', 'Художник-график', 'http://localhost:8080/api/files/avatars/artist4.jpg'),
('david.sculptor@gmail.com', 'hashed_password_10', 'Давид Морозов', 'ARTIST', '+79161234576', 'Скульптор', 'http://localhost:8080/api/files/avatars/artist5.jpg');
UPDATE "users"
SET registration_date = CURRENT_TIMESTAMP - (random() * INTERVAL '365 days')
WHERE registration_date IS NULL;
UPDATE "users"
SET is_active = true
WHERE is_active IS NULL;


-- Заполнение галерей
INSERT INTO gallery (name, description, address, contact_phone, contact_email, logo_url, status, admin_comment) VALUES
('Галерея Современного Искусства', 'Крупнейшая галерея современного искусства в городе', 'ул. Тверская, д. 25, Москва', '+74951234567', 'info@modernart.ru', 'http://localhost:8080/api/files/logos/gallery1.jpg', 'APPROVED', 'Проверена и одобрена'),
('Классическая Галерея', 'Галерея классического и академического искусства', 'ул. Арбат, д. 42, Москва', '+74951234568', 'contact@classicart.com', 'http://localhost:8080/api/files/logos/gallery2.jpg', 'APPROVED', 'Традиционная галерея высокого уровня'),
('Арт-Пространство "Восток-Запад"', 'Международная галерея современного искусства', 'пр. Мира, д. 15, Москва', '+74951234569', 'info@artspace.ru', 'http://localhost:8080/api/files/logos/gallery3.jpg', 'PENDING', 'На рассмотрении'),
('Галерея "Новые Имена"', 'Молодая галерея, поддерживающая начинающих художников', 'ул. Пушкинская, д. 8, Москва', '+74951234570', 'new.names@gallery.ru', 'http://localhost:8080/api/files/logos/gallery4.jpg', 'APPROVED', 'Перспективная молодая галерея'),
('Экспериментальная Галерея', 'Авангардные и экспериментальные проекты', 'ул. Новый Арбат, д. 33, Москва', '+74951234571', 'experiment@art.ru', 'http://localhost:8080/api/files/logos/gallery5.jpg', 'REJECTED', 'Не соответствует требованиям платформы');


-- Заполнение владения галереями
INSERT INTO gallery_ownership (gallery_id, owner_id, is_primary, verification_code, code_expiry) VALUES
(1, 3, TRUE, 'ABC123', NOW() + INTERVAL '7 days'),
(2, 4, TRUE, 'DEF456', NOW() + INTERVAL '7 days'),
(3, 5, TRUE, 'GHI789', NOW() + INTERVAL '7 days'),
(4, 3, FALSE, 'JKL012', NOW() + INTERVAL '7 days'),
(5, 4, FALSE, 'MNO345', NOW() + INTERVAL '7 days');


-- Заполнение выставочных событий
INSERT INTO exhibition_event (gallery_id, title, description, start_date, end_date, status) VALUES
(1, 'Современное искусство 2024', 'Ежегодная выставка современных художников', '2024-03-01 10:00:00', '2024-04-01 20:00:00', 'ACTIVE'),
(1, 'Абстрактные миры', 'Выставка абстрактного искусства', '2024-04-15 10:00:00', '2024-05-15 20:00:00', 'DRAFT'),
(2, 'Классика и современность', 'Диалог классического и современного искусства', '2024-03-10 11:00:00', '2024-04-10 19:00:00', 'ACTIVE'),
(4, 'Молодые таланты', 'Выставка начинающих художников', '2024-03-20 10:00:00', '2024-04-20 20:00:00', 'ACTIVE'),
(2, 'Зимняя сказка', 'Сезонная выставка зимних пейзажей', '2024-12-01 10:00:00', '2024-12-31 20:00:00', 'DRAFT');


-- Заполнение карт выставочных залов
INSERT INTO exhibition_hall_map (exhibition_event_id, map_image_url, name) VALUES
(1, 'http://localhost:8080/api/files/maps/hall1.jpg', 'Главный зал'),
(1, 'http://localhost:8080/api/files/maps/hall2.jpg', 'Малый зал'),
(3, 'http://localhost:8080/api/files/maps/hall3.jpg', 'Центральный зал'),
(4, 'http://localhost:8080/api/files/maps/hall4.jpg', 'Экспозиционный зал'),
(4, 'http://localhost:8080/api/files/maps/hall5.jpg', 'Галерейное пространство');


-- Заполнение выставочных стендов
INSERT INTO exhibition_stand (exhibition_hall_map_id, stand_number, position_x, position_y, width, height, type, status) VALUES
-- Для выставки 1 (Главный зал)
(1, 'A1', 10, 10, 300, 200, 'WALL', 'AVAILABLE'),
(1, 'A2', 320, 10, 300, 200, 'WALL', 'BOOKED'),
(1, 'A3', 630, 10, 300, 200, 'WALL', 'AVAILABLE'),
(1, 'B1', 50, 250, 400, 300, 'BOOTH', 'BOOKED'),
(1, 'B2', 470, 250, 400, 300, 'BOOTH', 'AVAILABLE'),


-- Для выставки 1 (Малый зал)
(2, 'C1', 20, 20, 250, 180, 'WALL', 'AVAILABLE'),
(2, 'C2', 290, 20, 250, 180, 'WALL', 'BOOKED'),
(2, 'D1', 100, 220, 350, 250, 'OPEN_SPACE', 'AVAILABLE'),


-- Для выставки 3
(3, 'E1', 15, 15, 280, 190, 'WALL', 'BOOKED'),
(3, 'E2', 310, 15, 280, 190, 'WALL', 'AVAILABLE'),
(3, 'F1', 80, 230, 400, 280, 'BOOTH', 'BOOKED'),


-- Для выставки 4
(4, 'G1', 25, 25, 270, 170, 'WALL', 'AVAILABLE'),
(4, 'G2', 315, 25, 270, 170, 'WALL', 'AVAILABLE'),
(5, 'H1', 60, 60, 500, 350, 'OPEN_SPACE', 'BOOKED');


-- Заполнение бронирований
INSERT INTO bookings (exhibition_stand_id, artist_id, booking_date, status) VALUES
(2, 6, '2024-02-15 14:30:00', 'CONFIRMED'),  -- Петр Иванов бронирует стенд A2
(4, 7, '2024-02-16 10:15:00', 'CONFIRMED'),  -- Анна Сидорова бронирует стенд B1
(7, 8, '2024-02-17 11:45:00', 'CONFIRMED'),  -- Михаил Орлов бронирует стенд C2
(9, 9, '2024-02-18 09:20:00', 'CONFIRMED'),  -- Екатерина Новикова бронирует стенд E1
(11, 10, '2024-02-19 16:00:00', 'CONFIRMED'), -- Давид Морозов бронирует стенд F1
(13, 6, '2024-02-20 13:10:00', 'PENDING'),   -- Петр Иванов бронирует стенд H1 (ожидание)
(5, 7, '2024-02-14 15:30:00', 'CANCELLED');  -- Анна Сидорова отменила бронь
ALTER TABLE users ALTER COLUMN id TYPE bigint;
ALTER TABLE bookings ALTER COLUMN id TYPE bigint;
ALTER TABLE bookings ALTER COLUMN artist_id TYPE bigint;
ALTER TABLE bookings ALTER COLUMN gallery_id TYPE bigint;
ALTER TABLE artworks ALTER COLUMN id TYPE bigint;
ALTER TABLE artworks ALTER COLUMN booking_id TYPE bigint;




-- Заполнение произведений искусства
INSERT INTO artworks (booking_id, title, description, creation_year, technique, image_url, status) VALUES
(1, 'Городские огни', 'Абстрактная композиция, вдохновленная ночным городом', 2023, 'Масло, холст', 'http://localhost:8080/api/files/artworks/art1.jpg', 'PUBLISHED'),
(1, 'Ритмы мегаполиса', 'Динамичная абстракция городской жизни', 2024, 'Акрил, холст', 'http://localhost:8080/api/files/artworks/art2.jpg', 'PUBLISHED'),
(2, 'Портрет незнакомки', 'Загадочный женский портрет', 2023, 'Масло, холст', 'http://localhost:8080/api/files/artworks/art3.jpg', 'PUBLISHED'),
(2, 'Внутренний мир', 'Психологический портрет', 2024, 'Пастель, бумага', 'http://localhost:8080/api/files/artworks/art4.jpg', 'DRAFT'),
(3, 'Утренний туман', 'Пейзаж с элементами импрессионизма', 2023, 'Акварель, бумага', 'http://localhost:8080/api/files/artworks/art5.jpg', 'PUBLISHED'),
(4, 'Графические фантазии', 'Серия графических работ', 2024, 'Графика, тушь', 'http://localhost:8080/api/files/artworks/art6.jpg', 'PUBLISHED'),
(5, 'Форма и пространство', 'Абстрактная скульптура из бронзы', 2023, 'Бронза', 'http://localhost:8080/api/files/artworks/art7.jpg', 'PUBLISHED'),
(5, 'Динамика движения', 'Кинетическая скульптура', 2024, 'Металл, двигатель', 'http://localhost:8080/api/files/artworks/art8.jpg', 'PUBLISHED');


ALTER TABLE artworks ALTER COLUMN id TYPE bigint;


-- Заполнение логов аудита администраторов
INSERT INTO admin_audit_log (admin_id, action, target_entity_id) VALUES
(1, 'APPROVE_GALLERY', 1),
(1, 'APPROVE_GALLERY', 2),
(2, 'APPROVE_GALLERY', 4),
(1, 'REJECT_GALLERY', 5),
(2, 'UPDATE_EXHIBITION', 1);
UPDATE "admin_audit_log"
SET timestamp = CURRENT_TIMESTAMP - (random() * INTERVAL '365 days')
WHERE timestamp IS NULL;


-- Заполнение логов активности пользователей
INSERT INTO user_activity_log (user_id, action) VALUES
(6, 'LOGIN'),
(6, 'CREATE_BOOKING'),
(7, 'LOGIN'),
(7, 'CREATE_BOOKING'),
(8, 'LOGIN'),
(8, 'CREATE_BOOKING'),
(9, 'LOGIN'),
(9, 'CREATE_BOOKING'),
(10, 'LOGIN'),
(10, 'CREATE_BOOKING'),
(3, 'LOGIN'),
(3, 'UPDATE_GALLERY'),
(4, 'LOGIN'),
(4, 'CREATE_EXHIBITION');
UPDATE "user_activity_log"
SET timestamp = CURRENT_TIMESTAMP - (random() * INTERVAL '365 days')
WHERE timestamp IS NULL;


-- Обновление последовательностей после вставки данных
SELECT setval('user_id_seq', (SELECT MAX(id) FROM "users"));
SELECT setval('gallery_id_seq', (SELECT MAX(id) FROM gallery));
SELECT setval('gallery_ownership_id_seq', (SELECT MAX(id) FROM gallery_ownership));
SELECT setval('exhibition_event_id_seq', (SELECT MAX(id) FROM exhibition_event));
SELECT setval('exhibition_hall_map_id_seq', (SELECT MAX(id) FROM exhibition_hall_map));
SELECT setval('exhibition_stand_id_seq', (SELECT MAX(id) FROM exhibition_stand));
SELECT setval('booking_id_seq', (SELECT MAX(id) FROM bookings));
SELECT setval('artwork_id_seq', (SELECT MAX(id) FROM artworks));
SELECT setval('admin_audit_log_id_seq', (SELECT MAX(id) FROM admin_audit_log));
SELECT setval('user_activity_log_id_seq', (SELECT MAX(id) FROM user_activity_log));

