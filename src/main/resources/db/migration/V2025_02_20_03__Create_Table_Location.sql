-- Bảng tỉnh/thành phố
CREATE SEQUENCE province_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO CYCLE;
GO
CREATE TABLE provinces
( province_id BIGINT PRIMARY KEY
, province_name NVARCHAR(255) NOT NULL
, region_id BIGINT FOREIGN KEY (region_id) REFERENCES regions(region_id) ON DELETE CASCADE
)