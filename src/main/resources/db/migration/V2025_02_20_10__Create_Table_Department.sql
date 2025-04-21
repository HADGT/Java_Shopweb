-- Bảng Phòng Ban (Departments)
CREATE TABLE departments
( department_id VARCHAR(36) PRIMARY KEY
, department_name NVARCHAR(255) NOT NULL
, location_id VARCHAR(36) FOREIGN KEY (location_id) references locations (location_id) ON DELETE SET DEFAULT
, description TEXT
)