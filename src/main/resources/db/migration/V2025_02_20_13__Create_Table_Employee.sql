CREATE TABLE employees
( employee_id VARCHAR(36) PRIMARY KEY
, first_name NVARCHAR(255) NOT NULL
, last_name NVARCHAR(255) NOT NULL
, email VARCHAR(255) UNIQUE
, phone_number VARCHAR(20) UNIQUE NOT NULL
, created_at DATETIME DEFAULT CURRENT_TIMESTAMP
, job_id VARCHAR(36) FOREIGN KEY (job_id) REFERENCES jobs(job_id) ON DELETE SET DEFAULT
, salary NUMERIC(10,3) DEFAULT 0 -- CHECK (salary >= 0)
, commission_pct NUMERIC(2,2) DEFAULT 0 -- CHECK (commission_pct BETWEEN 0 AND 1) -- HOA HỒNG
, status BIT DEFAULT 1 NOT NULL  -- 1: Đang hoạt động, 0: Không hoạt động
, role_id VARCHAR(36) FOREIGN KEY (role_id) REFERENCES roles(role_id)
, updated_at DATETIME
, location_id VARCHAR(36) references locations (location_id) ON DELETE SET DEFAULT
, description TEXT
)