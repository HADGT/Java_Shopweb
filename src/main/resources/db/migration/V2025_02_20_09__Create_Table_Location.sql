-- Bảng Địa Điểm (Locations)
CREATE TABLE locations
( location_id VARCHAR(36) PRIMARY KEY
, street_address NVARCHAR(255) NOT NULL
, district_id VARCHAR(36) FOREIGN KEY (district_id) REFERENCES districts(district_id) ON DELETE CASCADE
)