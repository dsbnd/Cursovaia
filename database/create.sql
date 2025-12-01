-- Создание перечислений (ENUM types)
CREATE TYPE user_role AS ENUM ('ADMIN', 'GALLERY_OWNER', 'ARTIST');
CREATE TYPE gallery_status AS ENUM ('PENDING', 'APPROVED', 'REJECTED');
CREATE TYPE exhibition_status AS ENUM ('DRAFT', 'ACTIVE', 'FINISHED');
CREATE TYPE stand_type AS ENUM ('WALL', 'BOOTH', 'OPEN_SPACE');
CREATE TYPE stand_status AS ENUM ('AVAILABLE', 'BOOKED');
CREATE TYPE booking_status AS ENUM ('PENDING', 'CONFIRMED', 'CANCELLED');
CREATE TYPE artwork_status AS ENUM ('DRAFT', 'PUBLISHED');


-- Таблица пользователей
CREATE TABLE "users" (
   id SERIAL PRIMARY KEY,
   email VARCHAR(255) UNIQUE NOT NULL,
   password_hash VARCHAR(255) NOT NULL,
   full_name VARCHAR(255) NOT NULL,
   role user_role NOT NULL,
   phone_number VARCHAR(20),
   bio TEXT,
   avatar_url VARCHAR(500)
);
ALTER TABLE users ADD COLUMN is_active BOOLEAN DEFAULT true;
ALTER TABLE users ADD COLUMN registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Таблица галерей
CREATE TABLE gallery (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   description TEXT,
   address VARCHAR(500) NOT NULL,
   contact_phone VARCHAR(20),
   contact_email VARCHAR(255) NOT NULL,
   logo_url VARCHAR(500),
   status gallery_status DEFAULT 'PENDING',
   admin_comment TEXT
);


-- Таблица владения галереями
CREATE TABLE gallery_ownership (
   id SERIAL PRIMARY KEY,
   gallery_id INTEGER NOT NULL,
   owner_id INTEGER NOT NULL,
   is_primary BOOLEAN DEFAULT FALSE,
   verification_code VARCHAR(10),
   code_expiry TIMESTAMP,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

   FOREIGN KEY (gallery_id) REFERENCES gallery(id) ON DELETE CASCADE,
   FOREIGN KEY (owner_id) REFERENCES "users"(id) ON DELETE CASCADE,
   UNIQUE(gallery_id, owner_id)
);


-- Таблица выставочных событий
CREATE TABLE exhibition_event (
   id SERIAL PRIMARY KEY,
   gallery_id INTEGER NOT NULL,
   title VARCHAR(255) NOT NULL,
   description TEXT,
   start_date TIMESTAMP NOT NULL,
   end_date TIMESTAMP NOT NULL,
   status exhibition_status DEFAULT 'DRAFT',

   FOREIGN KEY (gallery_id) REFERENCES gallery(id) ON DELETE CASCADE,
   CHECK (end_date > start_date)
);


-- Таблица карт выставочных залов
CREATE TABLE exhibition_hall_map (
   id SERIAL PRIMARY KEY,
   exhibition_event_id INTEGER NOT NULL,
   map_image_url VARCHAR(500),
   name VARCHAR(255) NOT NULL,

   FOREIGN KEY (exhibition_event_id) REFERENCES exhibition_event(id) ON DELETE CASCADE
);


-- Таблица выставочных стендов
CREATE TABLE exhibition_stand (
   id SERIAL PRIMARY KEY,
   exhibition_hall_map_id INTEGER NOT NULL,
   stand_number VARCHAR(50) NOT NULL,
   position_x INTEGER NOT NULL,
   position_y INTEGER NOT NULL,
   width INTEGER NOT NULL,
   height INTEGER NOT NULL,
   type stand_type NOT NULL,
   status stand_status DEFAULT 'AVAILABLE',

   FOREIGN KEY (exhibition_hall_map_id) REFERENCES exhibition_hall_map(id) ON DELETE CASCADE,
   CHECK (width > 0 AND height > 0),
   CHECK (position_x >= 0 AND position_y >= 0)
);


-- Таблица бронирований
CREATE TABLE bookings (
   id SERIAL PRIMARY KEY,
   exhibition_stand_id INTEGER NOT NULL,
   artist_id INTEGER NOT NULL,
   booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   status booking_status DEFAULT 'PENDING',

   FOREIGN KEY (exhibition_stand_id) REFERENCES exhibition_stand(id) ON DELETE CASCADE,
   FOREIGN KEY (artist_id) REFERENCES "users"(id) ON DELETE CASCADE
);


-- Таблица произведений искусства
CREATE TABLE artworks (
   id SERIAL PRIMARY KEY,
   booking_id INTEGER NOT NULL,
   title VARCHAR(255) NOT NULL,
   description TEXT,
   creation_year INTEGER,
   technique VARCHAR(255),
   image_url VARCHAR(500),
   status artwork_status DEFAULT 'DRAFT',

   FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
   CHECK (creation_year <= EXTRACT(YEAR FROM CURRENT_DATE))
);


-- Таблица аудита администраторов
CREATE TABLE admin_audit_log (
   id SERIAL PRIMARY KEY,
   admin_id INTEGER NOT NULL,
   action VARCHAR(100) NOT NULL,
   target_entity_id INTEGER NOT NULL,
   timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

   FOREIGN KEY (admin_id) REFERENCES "users"(id) ON DELETE CASCADE
);


-- Таблица активности пользователей
CREATE TABLE user_activity_log (
   id SERIAL PRIMARY KEY,
   user_id INTEGER NOT NULL,
   action VARCHAR(100) NOT NULL,
   timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

   FOREIGN KEY (user_id) REFERENCES "users"(id) ON DELETE CASCADE
);

