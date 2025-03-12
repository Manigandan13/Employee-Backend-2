package employeemanage.ems;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import employeemanage.ems.Attendance.AttendanceRepo;

@Service
public class EmployeeService {
       @Autowired
       private EmployeeRepo employeeRepository;

       private static final int MAX_EMPLOYEE_LIMIT = 10;
       private static final long MAX_IMAGE_SIZE = 2 * 1024 * 1024;

       @Autowired
       private AttendanceRepo attendanceRepository;

   
       // Create or update employee
       public Employee saveEmployee(Employee employee, MultipartFile imageFile) throws IOException { 

        long currentEmployeeCount = employeeRepository.count();

        // Check if the employee count exceeds the limit
        if (currentEmployeeCount >= MAX_EMPLOYEE_LIMIT) {
            throw new RuntimeException("Cannot add more employees. The maximum limit of " + MAX_EMPLOYEE_LIMIT + " employees has been reached.");
        }

        if (imageFile != null && imageFile.getSize() > MAX_IMAGE_SIZE) {
            throw new RuntimeException("The image size exceeds the maximum allowed limit of 2MB.");
        }

           if(imageFile!=null){
            employee.setImageType(imageFile.getContentType());
           employee.setImageName(imageFile.getOriginalFilename());
           employee.setImage(imageFile.getBytes());
           }
           return employeeRepository.save(employee);
       }
   
       // Find employee by id
       public Optional<Employee> findById(Long id) {
           return employeeRepository.findById(id);
       }
   
       // Find employee by email
       public Employee findByEmail(String emailId) {
           return employeeRepository.findByEmailId(emailId);
       }
   
       // Get all employees
       public List<Employee> getAllEmployees() {
           return employeeRepository.findAll();
       }
   
       // Delete employee by id
       @Transactional
        public void deleteEmployee(Long employeeId) {
        // First, delete the related attendance records
            attendanceRepository.deleteByEmployeeId(employeeId);

        // Then delete the employee
            employeeRepository.deleteById(employeeId);
        }


   }
   