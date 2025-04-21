-- Bảng Quốc Gia (Countries)
CREATE TABLE countries
( country_id CHAR(2) PRIMARY KEY CHECK (country_id = UPPER(country_id))
, country_name NVARCHAR(255) NOT NULL
)