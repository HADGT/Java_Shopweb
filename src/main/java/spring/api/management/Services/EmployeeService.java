//package spring.api.management.Services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import spring.api.management.models.Employees;
//import spring.api.management.Respositories.EmployeeRepository;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class EmployeeService {
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    public List<Employees> getallEmployee() {
//        return employeeRepository.findAll();
//    }
//
//    public Employees getEmployeesbyId(UUID Id) {
//        return employeeRepository.findById(Id).orElse(null);
//    }
//
//    public Employees createEmployee(Employees employee) {
//        return employeeRepository.save(employee);
//    }
//
//    public void deleteEmployee(UUID Id){
//        employeeRepository.deleteById(Id);
//    }
//}
