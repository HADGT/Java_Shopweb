-- Bảng chức vụ
CREATE TABLE jobs
( job_id VARCHAR(36) PRIMARY KEY
, job_title NVARCHAR(50) NOT NULL
, min_salary NUMERIC(10,0) DEFAULT 0
, max_salary NUMERIC(10,0) DEFAULT 0
, description TEXT
)