-- Bảng Địa Điểm (Locations)
CREATE SEQUENCE location_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO CYCLE;
GO
CREATE TABLE locations
( location_id BIGINT PRIMARY KEY
, street_address NVARCHAR(255) NOT NULL
, district_id BIGINT FOREIGN KEY (district_id) REFERENCES districts(district_id) ON DELETE CASCADE
)