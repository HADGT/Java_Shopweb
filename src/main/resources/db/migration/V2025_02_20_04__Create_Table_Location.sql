-- Bảng quận/huyện
CREATE SEQUENCE district_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO CYCLE;
GO
CREATE TABLE districts
( district_id BIGINT PRIMARY KEY
, district_name NVARCHAR(255) NOT NULL
, province_id BIGINT FOREIGN KEY (province_id) REFERENCES provinces(province_id) ON DELETE CASCADE
)