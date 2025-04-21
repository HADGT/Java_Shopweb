-- Bảng tỉnh/thành phố
CREATE TABLE provinces
( province_id VARCHAR(36) PRIMARY KEY
, province_name NVARCHAR(255) NOT NULL
, region_id VARCHAR(36) FOREIGN KEY (region_id) REFERENCES regions(region_id) ON DELETE CASCADE
)