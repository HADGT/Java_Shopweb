-- Bảng Vùng miền
CREATE TABLE regions
( region_id VARCHAR(36) primary key
, region_name NVARCHAR(255) NOT NULL
,  country_id CHAR(2) FOREIGN KEY (country_id) REFERENCES countries(country_id) ON DELETE SET DEFAULT
)