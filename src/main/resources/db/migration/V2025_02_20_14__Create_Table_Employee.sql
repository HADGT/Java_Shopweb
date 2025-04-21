CREATE TABLE department_management
( management_id VARCHAR(36) PRIMARY KEY
, manager_id VARCHAR(36) REFERENCES employees(employee_id) ON DELETE CASCADE
, department_id VARCHAR(36) FOREIGN KEY (department_id) REFERENCES departments(department_id) ON DELETE CASCADE
, UNIQUE (manager_id, department_id)
)