/*
Created: 16. 03. 2021
Modified: 16. 03. 2021
Model: PostgreSQL 10
Database: PostgreSQL 10
*/


-- Drop relationships section -------------------------------------------------

ALTER TABLE "archive_reservations" DROP CONSTRAINT "Relationship11"
;
ALTER TABLE "archive_reservations" DROP CONSTRAINT "Relationship10"
;
ALTER TABLE "reviews" DROP CONSTRAINT "Relationship9"
;
ALTER TABLE "reviews" DROP CONSTRAINT "Relationship8"
;
ALTER TABLE "reservations" DROP CONSTRAINT "Relationship7"
;
ALTER TABLE "reservations" DROP CONSTRAINT "Relationship6"
;
ALTER TABLE "images" DROP CONSTRAINT "Relationship5"
;
ALTER TABLE "playfields" DROP CONSTRAINT "Relationship4"
;
ALTER TABLE "playfields" DROP CONSTRAINT "Relationship3"
;
ALTER TABLE "playfields" DROP CONSTRAINT "Relationship2"
;
ALTER TABLE "playfields" DROP CONSTRAINT "Relationship1"
;




-- Drop keys for tables section -------------------------------------------------

ALTER TABLE "archive_reservations" DROP CONSTRAINT "PK_archive_reservations"
;
ALTER TABLE "reviews" DROP CONSTRAINT "PK_reviews"
;
ALTER TABLE "images" DROP CONSTRAINT "PK_images"
;
ALTER TABLE "images" DROP CONSTRAINT "url"
;
ALTER TABLE "playfield_types" DROP CONSTRAINT "PK_playfield_types"
;
ALTER TABLE "reservations" DROP CONSTRAINT "PK_reservations"
;
ALTER TABLE "playfields" DROP CONSTRAINT "PK_playfields"
;
ALTER TABLE "reservation_statuses" DROP CONSTRAINT "PK_reservation_statuses"
;
ALTER TABLE "regions" DROP CONSTRAINT "PK_regions"
;
ALTER TABLE "regions" DROP CONSTRAINT "postcode"
;
ALTER TABLE "users" DROP CONSTRAINT "PK_users"
;
ALTER TABLE "users" DROP CONSTRAINT "email"
;


-- Drop indexes section -------------------------------------------------

DROP INDEX "IX_Relationship10"
;
DROP INDEX "IX_Relationship11"
;
DROP INDEX "IX_Relationship8"
;
DROP INDEX "IX_Relationship9"
;
DROP INDEX "IX_Relationship5"
;
DROP INDEX "IX_Relationship6"
;
DROP INDEX "IX_Relationship7"
;
DROP INDEX "IX_Relationship1"
;
DROP INDEX "IX_Relationship2"
;
DROP INDEX "IX_Relationship3"
;
DROP INDEX "IX_Relationship4"
;


-- Drop tables section ---------------------------------------------------

DROP TABLE "archive_reservations"
;
DROP TABLE "reviews"
;
DROP TABLE "images"
;
DROP TABLE "playfield_types"
;
DROP TABLE "reservations"
;
DROP TABLE "playfields"
;
DROP TABLE "reservation_statuses"
;
DROP TABLE "regions"
;
DROP TABLE "users"
;

-- Create domains section ------------------------------------------------

CREATE DOMAIN review_score AS INTEGER
DEFAULT 3
CHECK (VALUE BETWEEN 1 AND 5);

-- Create tables section -------------------------------------------------

-- Table users

CREATE TABLE "users"(
 "id" Serial NOT NULL,
 "name" Character varying(100) NOT NULL,
 "surname" Character varying(100) NOT NULL,
 "email" Character varying(255) NOT NULL,
 "phone" Character varying(20),
 "password" Character varying(255) NOT NULL,
 "created_at" Timestamp NOT NULL,
 "updated_at" Timestamp NOT NULL
)
WITH (
 autovacuum_enabled=true)
;

-- Add keys for table users

ALTER TABLE "users" ADD CONSTRAINT "PK_users" PRIMARY KEY ("id")
;

ALTER TABLE "users" ADD CONSTRAINT "email" UNIQUE ("email")
;

-- Table regions

CREATE TABLE "regions"(
 "id" Serial NOT NULL,
 "name" Character varying(100) NOT NULL,
 "postcode" Character varying(20) NOT NULL
)
WITH (
 autovacuum_enabled=true)
;

-- Add keys for table regions

ALTER TABLE "regions" ADD CONSTRAINT "PK_regions" PRIMARY KEY ("id")
;

ALTER TABLE "regions" ADD CONSTRAINT "postcode" UNIQUE ("postcode")
;

-- Table reservation_statuses

CREATE TABLE "reservation_statuses"(
 "id" Serial NOT NULL,
 "title" Character varying(100) NOT NULL,
 "description" Text NOT NULL
)
WITH (
 autovacuum_enabled=true)
;

-- Add keys for table reservation_statuses

ALTER TABLE "reservation_statuses" ADD CONSTRAINT "PK_reservation_statuses" PRIMARY KEY ("id")
;

-- Table playfields

CREATE TABLE "playfields"(
 "id" Serial NOT NULL,
 "title" Character varying(100) NOT NULL,
 "description" Text NOT NULL,
 "phone" Character varying(20),
 "email" Character varying(100),
 "website" Character varying(255),
 "address" Character varying(100) NOT NULL,
 "avg_score" Real DEFAULT 3 NOT NULL,
 "price_per_hour" Real DEFAULT 0 NOT NULL,
 "created_at" Timestamp NOT NULL,
 "updated_at" Timestamp NOT NULL,
 "pending_reservations" Bigint DEFAULT 0 NOT NULL,
 "owner_id" Integer NOT NULL,
 "title_image_id" Integer,
 "type_id" Integer NOT NULL,
 "region_id" Integer NOT NULL
)
WITH (
 autovacuum_enabled=true)
;

-- Create indexes for table playfields

CREATE INDEX "IX_Relationship1" ON "playfields" ("owner_id")
;

CREATE INDEX "IX_Relationship2" ON "playfields" ("title_image_id")
;

CREATE INDEX "IX_Relationship3" ON "playfields" ("type_id")
;

CREATE INDEX "IX_Relationship4" ON "playfields" ("region_id")
;

-- Add keys for table playfields

ALTER TABLE "playfields" ADD CONSTRAINT "PK_playfields" PRIMARY KEY ("id")
;

-- Table reservations

CREATE TABLE "reservations"(
 "id" Serial NOT NULL,
 "order_date" Timestamp NOT NULL,
 "from_date" Timestamp NOT NULL,
 "to_date" Timestamp NOT NULL,
 "paid" Boolean DEFAULT false NOT NULL,
 "user_id" Integer NOT NULL,
 "status_id" Integer NOT NULL
)
WITH (
 autovacuum_enabled=true)
;

-- Create indexes for table reservations

CREATE INDEX "IX_Relationship6" ON "reservations" ("user_id")
;

CREATE INDEX "IX_Relationship7" ON "reservations" ("status_id")
;

-- Add keys for table reservations

ALTER TABLE "reservations" ADD CONSTRAINT "PK_reservations" PRIMARY KEY ("id")
;

-- Table playfield_types

CREATE TABLE "playfield_types"(
 "id" Serial NOT NULL,
 "name" Character varying(100) NOT NULL,
 "description" Text NOT NULL
)
WITH (
 autovacuum_enabled=true)
;

-- Add keys for table playfield_types

ALTER TABLE "playfield_types" ADD CONSTRAINT "PK_playfield_types" PRIMARY KEY ("id")
;

-- Table images

CREATE TABLE "images"(
 "id" Serial NOT NULL,
 "url" Character varying(25) NOT NULL,
 "caption" Text,
 "playfield_id" Integer NOT NULL
)
WITH (
 autovacuum_enabled=true)
;

-- Create indexes for table images

CREATE INDEX "IX_Relationship5" ON "images" ("playfield_id")
;

-- Add keys for table images

ALTER TABLE "images" ADD CONSTRAINT "PK_images" PRIMARY KEY ("id")
;

ALTER TABLE "images" ADD CONSTRAINT "url" UNIQUE ("url")
;

-- Table reviews

CREATE TABLE "reviews"(
 "id" Serial NOT NULL,
 "message" Text NOT NULL,
 "score" review_score NOT NULL,
 "created_at" Timestamp NOT NULL,
 "updated_at" Timestamp NOT NULL,
 "user_id" Integer NOT NULL,
 "playfield_id" Integer NOT NULL
)
WITH (
 autovacuum_enabled=true)
;

-- Create indexes for table reviews

CREATE INDEX "IX_Relationship8" ON "reviews" ("user_id")
;

CREATE INDEX "IX_Relationship9" ON "reviews" ("playfield_id")
;

-- Add keys for table reviews

ALTER TABLE "reviews" ADD CONSTRAINT "PK_reviews" PRIMARY KEY ("id")
;

-- Table archive_reservations

CREATE TABLE "archive_reservations"(
 "id" Serial NOT NULL,
 "order_date" Timestamp NOT NULL,
 "from_date" Timestamp NOT NULL,
 "to_date" Timestamp NOT NULL,
 "paid" Boolean DEFAULT false NOT NULL,
 "created_at" Timestamp NOT NULL,
 "user_id" Integer,
 "playfield_id" Integer NOT NULL
)
WITH (
 autovacuum_enabled=true)
;

-- Create indexes for table archive_reservations

CREATE INDEX "IX_Relationship10" ON "archive_reservations" ("user_id")
;

CREATE INDEX "IX_Relationship11" ON "archive_reservations" ("playfield_id")
;

-- Add keys for table archive_reservations

ALTER TABLE "archive_reservations" ADD CONSTRAINT "PK_archive_reservations" PRIMARY KEY ("id")
;
-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE "playfields" ADD CONSTRAINT "Relationship1" FOREIGN KEY ("owner_id") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE "playfields" ADD CONSTRAINT "Relationship2" FOREIGN KEY ("title_image_id") REFERENCES "images" ("id") ON DELETE SET NULL ON UPDATE CASCADE
;

ALTER TABLE "playfields" ADD CONSTRAINT "Relationship3" FOREIGN KEY ("type_id") REFERENCES "playfield_types" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
;

ALTER TABLE "playfields" ADD CONSTRAINT "Relationship4" FOREIGN KEY ("region_id") REFERENCES "regions" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
;

ALTER TABLE "images" ADD CONSTRAINT "Relationship5" FOREIGN KEY ("playfield_id") REFERENCES "playfields" ("id") ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE "reservations" ADD CONSTRAINT "Relationship6" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE "reservations" ADD CONSTRAINT "Relationship7" FOREIGN KEY ("status_id") REFERENCES "reservation_statuses" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
;

ALTER TABLE "reviews" ADD CONSTRAINT "Relationship8" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE "reviews" ADD CONSTRAINT "Relationship9" FOREIGN KEY ("playfield_id") REFERENCES "playfields" ("id") ON DELETE CASCADE ON UPDATE CASCADE
;

ALTER TABLE "archive_reservations" ADD CONSTRAINT "Relationship10" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE SET NULL ON UPDATE CASCADE
;

ALTER TABLE "archive_reservations" ADD CONSTRAINT "Relationship11" FOREIGN KEY ("playfield_id") REFERENCES "playfields" ("id") ON DELETE CASCADE ON UPDATE CASCADE
;




