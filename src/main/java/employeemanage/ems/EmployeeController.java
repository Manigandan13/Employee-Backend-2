package employeemanage.ems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Create or update employee
    @PostMapping
    public ResponseEntity<Employee> createOrUpdateEmployee(@RequestPart Employee employee,@RequestPart(required=false) MultipartFile imageFile) throws IOException {
        Employee updatedEmployee = employeeService.saveEmployee(employee,imageFile);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.CREATED);
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.findById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        System.out.println(employeeService.getAllEmployees());
        return employeeService.getAllEmployees();
    }

    // Delete employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable Long id){
        Optional<Employee> employee = employeeService.findById(id);
        byte[] imageFile = employee.get().getImage();
        return ResponseEntity.ok().body(imageFile);
    }
}
