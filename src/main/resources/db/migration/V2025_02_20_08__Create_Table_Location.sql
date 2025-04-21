-- Bảng quận/huyện
CREATE TABLE districts
( district_id VARCHAR(36) PRIMARY KEY
, district_name NVARCHAR(255) NOT NULL
, province_id VARCHAR(36) FOREIGN KEY (province_id) REFERENCES provinces(province_id) ON DELETE CASCADE
)