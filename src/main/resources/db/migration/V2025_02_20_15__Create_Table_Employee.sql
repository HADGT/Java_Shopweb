CREATE TABLE employees_management
( management_id VARCHAR(36) FOREIGN KEY (management_id) REFERENCES department_management(management_id) ON DELETE CASCADE
, employee_id VARCHAR(36) FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
, PRIMARY KEY (management_id, employee_id) -- Tạo khóa chính duy nhất cho cặp management_id và employee_id
)