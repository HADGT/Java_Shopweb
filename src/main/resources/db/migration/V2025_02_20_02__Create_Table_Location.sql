-- Bảng Vùng miền
CREATE SEQUENCE region_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO CYCLE;
GO
CREATE TABLE regions
( region_id BIGINT primary key
, region_name NVARCHAR(255) NOT NULL
,  country_id CHAR(2) FOREIGN KEY (country_id) REFERENCES countries(country_id) ON DELETE SET DEFAULT
)